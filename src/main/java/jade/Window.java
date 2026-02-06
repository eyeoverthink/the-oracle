package jade;

import fraymus.*;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import renderer.DebugDraw;
import renderer.Framebuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {

    private int width, height;
    private String title;
    private long glfwWindow;
    private ImGuiLayer imGuiLayer;
    private Framebuffer framebuffer;
    private Camera camera;
    private PhiWorld phiWorld;
    private ExperimentManager experimentManager;

    private static Window window = null;

    private float dt;
    private float physicsAccumulator = 0.0f;
    private static final float PHYSICS_DT = 1.0f / 60.0f;

    private Window() {
        this.width = 1280;
        this.height = 720;
        this.title = "Fraymus Engine V2";
    }

    public static Window get() {
        if (window == null) {
            window = new Window();
        }
        return window;
    }

    public void run() {
        init();
        loop();
        cleanup();
    }

    private void init() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_FALSE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
        glfwWindowHint(GLFW_CONTEXT_CREATION_API, GLFW_EGL_CONTEXT_API);

        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
        if (glfwWindow == NULL) {
            System.out.println("EGL context failed, retrying with native context API...");
            glfwDefaultWindowHints();
            glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE);
            glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
            glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
            glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
            glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
            glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
            glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
            if (glfwWindow == NULL) {
                throw new IllegalStateException("Failed to create GLFW window (tried EGL and native)");
            }
        }

        System.out.println("[Fraymus] Window created successfully");

        glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);
        glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);

        glfwSetWindowSizeCallback(glfwWindow, (w, newWidth, newHeight) -> {
            Window.get().setWidth(newWidth);
            Window.get().setHeight(newHeight);
        });

        glfwMakeContextCurrent(glfwWindow);
        glfwSwapInterval(1);
        glfwShowWindow(glfwWindow);

        GL.createCapabilities();

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        framebuffer = new Framebuffer(1920, 1080);

        camera = new Camera(new Vector2f(0.0f, 0.0f));

        imGuiLayer = new ImGuiLayer(glfwWindow);
        imGuiLayer.initImGui();

        DebugDraw.start();

        FraymusUI.setArenaTextureId(framebuffer.getTextureId());

        initPhiWorld();
    }

    private void initPhiWorld() {
        phiWorld = new PhiWorld();
        GenesisMemory memory = phiWorld.getMemory();

        phiWorld.addLaw(new Laws.Inertia());
        phiWorld.addLaw(new Laws.HarmonicResonance());
        phiWorld.addLaw(new Laws.EntanglementLaw(memory));
        phiWorld.addLaw(new Laws.ScottPredictionLaw(2.0f));
        phiWorld.addLaw(new Laws.ResonanceSpikeLaw(memory));
        phiWorld.addLaw(new Laws.BrainLaw(phiWorld, memory));
        phiWorld.addLaw(new Laws.ReproductionLaw(phiWorld, memory));
        phiWorld.addLaw(new Laws.BoundaryLaw(-180.0f, 180.0f, -100.0f, 100.0f));

        PhiNode alpha = new PhiNode("Alpha", 0.0f, 0.0f);
        alpha.vx = 0.5f;
        alpha.vy = 0.3f;
        alpha.energy = 1.0f;

        PhiNode beta = new PhiNode("Beta", 30.0f, 20.0f);
        beta.vx = -0.3f;
        beta.vy = 0.2f;
        beta.energy = 0.9f;

        PhiNode gamma = new PhiNode("Gamma", -20.0f, 40.0f);
        gamma.vx = 0.1f;
        gamma.vy = -0.4f;
        gamma.energy = 0.8f;

        PhiNode delta = new PhiNode("Delta", 50.0f, -30.0f);
        delta.vx = -0.2f;
        delta.vy = 0.1f;
        delta.energy = 0.95f;

        PhiNode epsilon = new PhiNode("Epsilon", -40.0f, -20.0f);
        epsilon.vx = 0.4f;
        epsilon.vy = 0.4f;
        epsilon.energy = 0.85f;

        phiWorld.addNode(alpha);
        phiWorld.addNode(beta);
        phiWorld.addNode(gamma);
        phiWorld.addNode(delta);
        phiWorld.addNode(epsilon);

        experimentManager = new ExperimentManager(phiWorld);
        CommandTerminal.init(experimentManager);

        FraymusUI.addLog("World initialized with 5 PhiNode entities");
        FraymusUI.addLog("Laws: Inertia, Resonance, Entangle, Scott, Spike, Brain, Reproduction, Boundary");
        FraymusUI.addLog("Genesis Memory chain started - recording all events");
        FraymusUI.addLog("Terminal ready - type 'help' for commands");
    }

    private void loop() {
        float beginTime = (float) glfwGetTime();
        float endTime;

        while (!glfwWindowShouldClose(glfwWindow)) {
            glfwPollEvents();

            if (MouseListener.getScrollY() != 0) {
                camera.addZoom(-MouseListener.getScrollY() * 0.1f);
            }

            if (MouseListener.isDragging() && MouseListener.isMouseButtonDown(2)) {
                camera.getPosition().x -= MouseListener.getDx() * camera.getZoom();
                camera.getPosition().y += MouseListener.getDy() * camera.getZoom();
            }

            DebugDraw.beginFrame();

            long nowNanos = System.nanoTime();
            physicsAccumulator += dt;
            while (physicsAccumulator >= PHYSICS_DT) {
                phiWorld.step(PHYSICS_DT, nowNanos);
                physicsAccumulator -= PHYSICS_DT;
            }

            framebuffer.bind();
            glClearColor(0.05f, 0.05f, 0.1f, 1.0f);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            glViewport(0, 0, 1920, 1080);

            FraymusRenderer.render(phiWorld, camera);
            DebugDraw.draw();

            framebuffer.unbind();

            glClearColor(0.05f, 0.05f, 0.1f, 1.0f);
            glClear(GL_COLOR_BUFFER_BIT);
            glViewport(0, 0, width, height);

            imGuiLayer.startFrame(dt);
            FraymusUI.render(phiWorld);
            imGuiLayer.endFrame();

            glfwSwapBuffers(glfwWindow);
            MouseListener.endFrame();

            endTime = (float) glfwGetTime();
            dt = endTime - beginTime;
            beginTime = endTime;
        }
    }

    private void cleanup() {
        imGuiLayer.destroyImGui();
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);
        glfwTerminate();
        GLFWErrorCallback errCb = glfwSetErrorCallback(null);
        if (errCb != null) {
            errCb.free();
        }
    }

    public static int getWidth() {
        return get().width;
    }

    public static int getHeight() {
        return get().height;
    }

    public static Camera getCamera() {
        return get().camera;
    }

    public static PhiWorld getPhiWorld() {
        return get().phiWorld;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
