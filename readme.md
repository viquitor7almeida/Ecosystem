# BIO-SYNTH | Advanced Chemical Evolution Simulator

**BIO-SYNTH** is a biological simulation engine developed in Java designed to explore emergent complexity through chemical evolution algorithms. The project utilizes parallel processing to simulate thousands of cellular interactions in real-time, enabling the observation of colonies, mutations, and symbiotic behaviors.

---

## Project Highlights

### Decoupled Architecture (SOLID)
Unlike conventional simulators, **BIO-SYNTH** applies the Open/Closed Principle (OCP). The rendering engine and the calculation core are independent of the evolution rules.
*   **Engine:** Responsible for matrix management and data lifecycle.
*   **Rules:** Pluggable interfaces allow for the injection of new survival logics without modifying the system's core.

### Parallel Processing (Multithreading)
To ensure high performance in high-density matrices, the simulation efficiently distributes the workload:
*   The matrix is fragmented into regions processed simultaneously by different CPU cores.
*   Implementation focuses on maximizing data throughput using Java's native thread management.

### Bio-Luminescent Visualization
The interface was developed in Java Swing with a focus on laboratory aesthetics and visual clarity:
*   Rendering via **Graphics2D** with Anti-aliasing to eliminate jagged edges.
*   Dynamic glow effects and pulsation animations that simulate living membrane activity.
*   Interactive dashboard featuring an evolution timer and support for direct manipulation via peripherals.

---

## Simulation Concepts

The ecosystem supports multiple cellular states with distinct behaviors:

| Type | Behavior | Visual Identity |
| :--- | :--- | :--- |
| **Common** | Follows classical laws of survival and reproduction. | Neon Green |
| **Chemical** | Acts as a catalyst, facilitating the emergence of new cells. | Bright Cyan |
| **Resistant** | Reinforced membrane structure, surviving overpopulation. | Amber |

---

## Technologies Used

*   **Language:** Java 17+
*   **GUI:** Java Swing / Graphics2D
*   **Concurrency:** Java Util Concurrent (Executors / Threading)
*   **Design Patterns:** Strategy Pattern (Rules), MVC (Model-View-Controller)

---

## How to Run the Experiment

1.  **Clone the repository:**
    ```bash
    git clone [https://github.com/your-user/bio-synth.git](https://github.com/your-user/bio-synth.git)
    ```
2.  **Compile the modules:**
    ```bash
    javac src/Main.java
    ```
3.  **Launch the simulation:**
    ```bash
    java src/Main
    ```

---

## Evolution Roadmap

- High-Performance Interface completion.
- Implementation of Rule-based Architecture (OCP).
- Development of Parallel Processing for large-scale matrices (500x500).
- Random Genetic Mutation system.
- Data persistence and state exportation in JSON format.
