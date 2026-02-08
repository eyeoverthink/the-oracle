package fraymus.quantum.neural;

import fraymus.quantum.core.PhiHarmonicMath;
import java.util.*;

/**
 * Temporal Pattern Buffer with Exponential Decay
 * 
 * Stores patterns with timestamps and calculates weighted resonance
 * based on age and pattern matching.
 */
public class TemporalPatternBuffer {
    
    public static class PatternEntry {
        public final double[] pattern;
        public final long timestamp;
        public final double resonance;
        public final Set<String> categories;
        
        public PatternEntry(double[] pattern, double resonance, Set<String> categories) {
            this.pattern = pattern;
            this.timestamp = System.currentTimeMillis();
            this.resonance = resonance;
            this.categories = categories != null ? new HashSet<>(categories) : new HashSet<>();
        }
        
        /**
         * Calculate weight based on age and pattern matching
         * weight = e^(-age) * (1 + patternMatches)
         */
        public double calculateWeight(Set<String> currentCategories) {
            double ageSeconds = (System.currentTimeMillis() - timestamp) / 1000.0;
            int patternMatches = countMatches(currentCategories);
            return PhiHarmonicMath.weightedDecay(ageSeconds, patternMatches);
        }
        
        private int countMatches(Set<String> currentCategories) {
            if (currentCategories == null || categories == null) return 0;
            
            int count = 0;
            for (String cat : categories) {
                if (currentCategories.contains(cat)) count++;
            }
            return count;
        }
        
        public double getAgeSeconds() {
            return (System.currentTimeMillis() - timestamp) / 1000.0;
        }
    }
    
    private final List<PatternEntry> buffer;
    private final int timeWindowSeconds;
    private final int maxEntries;
    
    public TemporalPatternBuffer(int timeWindowSeconds) {
        this(timeWindowSeconds, 1000);
    }
    
    public TemporalPatternBuffer(int timeWindowSeconds, int maxEntries) {
        this.buffer = Collections.synchronizedList(new ArrayList<>());
        this.timeWindowSeconds = timeWindowSeconds;
        this.maxEntries = maxEntries;
    }
    
    /**
     * Add a new pattern to the buffer
     */
    public void addPattern(double[] pattern, double resonance, Set<String> categories) {
        buffer.add(new PatternEntry(pattern, resonance, categories));
        cleanOldPatterns();
        
        // Prevent buffer overflow
        while (buffer.size() > maxEntries) {
            buffer.remove(0);
        }
    }
    
    /**
     * Add pattern from single frequency value
     */
    public void addPattern(double frequency, double resonance, Set<String> categories) {
        addPattern(new double[]{frequency}, resonance, categories);
    }
    
    /**
     * Remove patterns older than time window
     */
    private void cleanOldPatterns() {
        long cutoff = System.currentTimeMillis() - (timeWindowSeconds * 1000L);
        buffer.removeIf(entry -> entry.timestamp < cutoff);
    }
    
    /**
     * Calculate weighted resonance from all patterns in buffer
     */
    public double calculateWeightedResonance(Set<String> currentCategories) {
        cleanOldPatterns();
        
        if (buffer.isEmpty()) return 1.0;
        
        double[] weights = new double[buffer.size()];
        double[] resonances = new double[buffer.size()];
        
        synchronized (buffer) {
            for (int i = 0; i < buffer.size(); i++) {
                PatternEntry entry = buffer.get(i);
                weights[i] = entry.calculateWeight(currentCategories);
                resonances[i] = entry.resonance;
            }
        }
        
        // Normalize weights
        double weightSum = 0;
        for (double w : weights) weightSum += w;
        
        if (weightSum > 0) {
            for (int i = 0; i < weights.length; i++) {
                weights[i] /= weightSum;
            }
        }
        
        return PhiHarmonicMath.calculateTemporalResonance(resonances, weights);
    }
    
    /**
     * Get the most recent pattern entry
     */
    public PatternEntry getMostRecent() {
        if (buffer.isEmpty()) return null;
        return buffer.get(buffer.size() - 1);
    }
    
    /**
     * Get all entries matching a specific category
     */
    public List<PatternEntry> getEntriesWithCategory(String category) {
        List<PatternEntry> result = new ArrayList<>();
        synchronized (buffer) {
            for (PatternEntry entry : buffer) {
                if (entry.categories.contains(category)) {
                    result.add(entry);
                }
            }
        }
        return result;
    }
    
    /**
     * Get buffer size
     */
    public int size() {
        return buffer.size();
    }
    
    /**
     * Clear the buffer
     */
    public void clear() {
        buffer.clear();
    }
    
    /**
     * Get average resonance of all entries
     */
    public double getAverageResonance() {
        if (buffer.isEmpty()) return 1.0;
        
        double sum = 0;
        synchronized (buffer) {
            for (PatternEntry entry : buffer) {
                sum += entry.resonance;
            }
        }
        return sum / buffer.size();
    }
}
