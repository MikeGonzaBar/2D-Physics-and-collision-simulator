# 2D Physics and Collision Simulator

A Java-based 2D physics simulation engine that demonstrates real-time collision detection, gravity effects, and multi-object interactions in a graphical environment.

## 🎯 Overview

This project implements a complete 2D physics simulation system with the following capabilities:
- Real-time collision detection between various 2D shapes
- Gravity simulation and force application
- Multi-threaded rendering and physics calculations
- Support for multiple geometric shapes (Rectangles, Circles, Triangles, Polygons)
- Configurable simulation parameters and window properties

## ✨ Features

### Physics Engine
- **Collision Detection**: Advanced collision detection between different 2D shapes
- **Gravity Simulation**: Realistic gravity effects applied to all objects
- **Force System**: Configurable force application and physics calculations
- **Velocity and Acceleration**: Full kinematic simulation with velocity and acceleration vectors

### Supported Shapes
- **Rectangles**: Axis-aligned rectangles with customizable dimensions
- **Circles**: Perfect circles with radius-based collision detection
- **Triangles**: Triangular shapes with vertex-based collision
- **Polygons**: Extensible polygon system for complex shapes

### Rendering System
- **60 FPS Rendering**: Smooth real-time visualization
- **Multi-threaded Architecture**: Separate threads for physics and rendering
- **Configurable Window**: Customizable window size, title, and scale
- **Real-time FPS Display**: On-screen frame rate monitoring

## 🏗️ Project Structure

```
src/ProyectoPOO/
├── Engine/                 # Core simulation engine
│   ├── SimEngine.java     # Main simulation controller
│   ├── Collisions.java    # Collision detection system
│   ├── Forces.java        # Physics forces and gravity
│   ├── Renderer.java      # Graphics rendering
│   ├── ShapeMove.java     # Object movement calculations
│   └── Window.java        # GUI window management
├── Shapes2D/              # Geometric shape implementations
│   ├── Shape2d.java       # Abstract base class for all shapes
│   ├── Circle.java        # Circle implementation
│   ├── Rectangle.java     # Rectangle implementation
│   ├── Triangle.java      # Triangle implementation
│   ├── Polygon.java       # Polygon implementation
│   └── Vector2D.java      # 2D vector mathematics
├── GenerateCircles.java   # Dynamic circle generation
└── Main.java             # Application entry point
```

## 🚀 Installation

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Any Java IDE (Eclipse, IntelliJ IDEA, NetBeans) or command line tools

### Building the Project

1. **Clone the repository**:
   ```bash
   git clone <repository-url>
   cd 2D-Physics-and-collision-simulator
   ```

2. **Compile the source code**:
   ```bash
   javac -d bin src/ProyectoPOO/**/*.java src/ProyectoPOO/*.java
   ```

3. **Run the simulation**:
   ```bash
   java -cp bin ProyectoPOO.Main
   ```

## 🎮 Usage Examples

### Basic Simulation Setup

```java
import ProyectoPOO.Engine.SimEngine;
import ProyectoPOO.Shapes2D.*;
import java.util.ArrayList;

public class Example {
    public static void main(String[] args) {
        ArrayList<Shape2d> shapes = new ArrayList<>();
        
        // Create a rectangle with initial velocity
        Rectangle rect = new Rectangle(100, 200, 80, 80);
        rect.setVelocity(new Vector2D(-500, 500));
        shapes.add(rect);
        
        // Create a circle
        Circle circle = new Circle(300, 100, 20);
        circle.setVelocity(new Vector2D(200, -300));
        shapes.add(circle);
        
        // Start the simulation
        SimEngine engine = new SimEngine(shapes, "My Physics Demo");
        engine.start();
    }
}
```

### Dynamic Object Generation

```java
// Enable automatic circle generation
Thread circleGenerator = new GenerateCircles(shapes);
circleGenerator.start();

// The generator will create circles until reaching 25 objects
```

### Custom Simulation Parameters

```java
// Create simulation with custom window size and scale
SimEngine engine = new SimEngine(
    shapes,           // List of shapes
    "Custom Demo",    // Window title
    1024,            // Width
    768,             // Height
    1.5f             // Scale factor
);
```

## 🔧 Configuration

### Simulation Parameters
- **Refresh Rate**: 60 FPS (configurable in `SimEngine.REFRESH_RATE`)
- **Gravity**: Applied to all objects (configurable in `Forces` class)
- **Window Size**: Default 720x640 pixels (customizable)
- **Scale Factor**: Default 1.0 (for zoom effects)

### Physics Settings
- **Collision Detection**: Real-time between all shape types
- **Gravity Direction**: Downward acceleration
- **Force Application**: Configurable force magnitude and direction
- **Object Limits**: Maximum 25 objects in simulation

## 🎨 Customization

### Adding New Shapes
1. Extend the `Shape2d` abstract class
2. Implement required methods: `getPerimeter()`, `getArea()`, `setCenter()`
3. Add collision detection logic in `Collisions.java`

### Modifying Physics
- Adjust gravity in `Forces.java`
- Modify collision response in `Collisions.java`
- Change movement calculations in `ShapeMove.java`

### Visual Customization
- Modify rendering in `Renderer.java`
- Adjust window properties in `Window.java`
- Change color schemes and visual effects

## 📊 Performance

- **Target FPS**: 60 frames per second
- **Physics Updates**: Synchronized with rendering
- **Memory Management**: Efficient object pooling and cleanup
- **Multi-threading**: Separate threads for physics and rendering

## 🤝 Contributing

This project was developed as a collaborative effort. To contribute:

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## 👥 Authors

- **Miguel Gonzalez Barajas** ([MikeGonzaBar](https://github.com/MikeGonzaBar))
- **Pedro Garcia Romero** ([DasPeter](https://github.com/DasPeter))
- **Kevin Gonzalez Gomez** ([kevinglezgmz](https://github.com/kevinglezgmz))

## 📄 License

This project is open source and available under the [MIT License](LICENSE).

## 🐛 Known Issues

- Complex polygon collision detection may have edge cases
- High object counts (>25) may impact performance
- Window resizing during simulation may cause visual artifacts

**Note**: This simulator is designed for educational and demonstration purposes. For production physics simulations, consider using established physics engines like Box2D or JBox2D. 