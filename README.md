# 2D Physics and Collision Simulator

Educational Java/Swing project for experimenting with a simple 2D physics loop,
basic vector math, gravity, rendering, and collision response between circles,
rectangles, and triangles.

The project is intentionally small: it is useful as an object-oriented
programming exercise and as a base for learning how a game-style update loop is
structured.

## Demo

Static platforms with dynamic circles and rectangles:

[Watch the static-platform demo](media/simulator-static-platforms.mp4)

Rectangle collision regression check:

[Watch the rectangle-collision demo](media/simulator-rectangle-collision-fixed.mp4)

## Features

- Fixed-step simulation loop targeting 60 physics updates per second.
- Swing/AWT rendering with a double-buffered canvas.
- Mutable `Vector2D` math for position, velocity, and acceleration.
- Shape hierarchy for circles, rectangles, triangles, and polygon-backed shapes.
- Gravity and linear damping applied to all simulated shapes.
- Static bodies for fixed platforms and walls.
- Per-shape mass for dynamic collision response.
- Collision handling for:
  - Circle vs circle
  - Circle vs rectangle
  - Circle vs triangle
  - Rectangle vs rectangle
  - Circles/rectangles vs window walls
- Small no-dependency smoke test suite.
- PowerShell scripts for build, test, run, and Javadoc generation.

## Requirements

- JDK 8 or newer.
- PowerShell for the included scripts.

The scripts look for Java in this order:

1. `.codex-tools/jdk-17`
2. `JAVA_HOME`
3. `PATH`

## Quick Start

Build:

```powershell
.\scripts\build.ps1
```

Run tests:

```powershell
.\scripts\test.ps1
```

Run the simulator:

```powershell
.\scripts\run.ps1
```

Generate Javadocs:

```powershell
.\scripts\javadoc.ps1
```

Generated files are written under `build/` and are ignored by git.

## Physics Behavior

Every `Shape2d` has three important body properties:

- `setStatic(true)` makes a shape a fixed, infinite-mass collision object.
- `setAffectedByGravity(false)` keeps a dynamic shape from receiving gravity.
- `setMass(value)` changes how much a dynamic body moves during collision
  resolution.

The demo uses static rectangles as platforms and dynamic rectangles/circles as
falling objects. This keeps platforms anchored while still allowing moving boxes
to collide, bounce, and stack.

## Manual Commands

If you prefer raw Java commands:

```powershell
javac -d build\classes (Get-ChildItem -Recurse src -Filter *.java).FullName
java -cp build\classes ProyectoPOO.Main
```

## Project Structure

```text
src/ProyectoPOO/
  Engine/
    SimEngine.java     Main simulation controller and update loop
    Collisions.java    Collision detection and response
    Forces.java        Gravity and force integration
    Renderer.java      Frame rendering
    ShapeMove.java     Position integration helper
    Window.java        Swing/AWT window and buffer strategy
  Shapes2D/
    Shape2d.java       Base class for simulated shapes
    Circle.java        Circle implementation
    Rectangle.java     Axis-aligned rectangle implementation
    Triangle.java      Isosceles triangle implementation
    Polygon.java       Base class for vertex-backed shapes
    Vector2D.java      Mutable 2D vector
  GenerateCircles.java Optional circle generator thread
  Main.java            Demo entry point

test/ProyectoPOO/
  PhysicsSmokeTests.java

scripts/
  build.ps1
  test.ps1
  run.ps1
  javadoc.ps1

media/
  simulator-static-platforms.mp4
  simulator-rectangle-collision-fixed.mp4
```

## Testing

The smoke tests cover:

- shape constructor behavior and invalid dimensions
- gravity, gravity opt-out, and static bodies
- wall collisions at corners
- circle-circle, circle-rectangle, and rectangle-rectangle separation
- exact-touch collisions that should not jitter
- same-center circle overlap recovery
- mass-weighted correction

Run them with:

```powershell
.\scripts\test.ps1
```

## Development Notes

- Keep generated `.class` files, Javadocs, and local tool downloads out of git.
- Add Javadocs for public API behavior, but avoid documenting trivial getters.
- Add tests for geometry math and collision edge cases before expanding the
  physics rules.
- Keep long-running recordings in `media/` only when they document meaningful
  behavior. One-off local recordings belong under ignored `build/visual/`.
- Use `shape.setStatic(true)` for fixed platforms. Static bodies are not moved
  by forces and behave as infinite-mass collision objects.
- Use `shape.setAffectedByGravity(false)` for dynamic objects that should move
  from velocity/collisions but ignore gravity.
- Use `shape.setMass(value)` to tune how much a dynamic object moves during
  collision resolution.
- The current collision response is intentionally simple and favors a readable
  classroom implementation over production-grade physical accuracy.

## Known Limitations

- Collision response uses simple mass-aware impulses, but does not model
  rotation, angular velocity, friction, or restitution per object.
- Complex polygon collision detection is not implemented.
- Triangle wall collisions are not currently handled.
- Object collision resolution is approximate and can behave oddly with very high
  speeds or deep overlaps.
- The simulator is best treated as a learning project, not a replacement for
  Box2D, JBox2D, or another mature physics engine.

## Authors

- Miguel Gonzalez Barajas ([MikeGonzaBar](https://github.com/MikeGonzaBar))
- Pedro Garcia Romero ([DasPeter](https://github.com/DasPeter))
- Kevin Gonzalez Gomez ([kevinglezgmz](https://github.com/kevinglezgmz))

## License

MIT. See [LICENSE](LICENSE).
