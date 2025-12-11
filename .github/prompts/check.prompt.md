## Frontend & JavaScript Naming Conventions

- **Variables** in JavaScript files must use **camelCase**.
- **Functions and methods** must include **JSDoc** documentation directly above their definition.
- **Component HTML tags** must use **kebab-case** and contain at least one hyphen (`-`).
- **Component file names** must match the component’s HTML tag exactly.
- **Component class names** must use **PascalCase**, corresponding to the component HTML tag but **without hyphens**.

---

## Backend Layer Architecture

### Presentation / API Layer
- Entry point for incoming requests and outgoing responses.

### Application / Service Layer
- Central coordination layer that translates use cases into domain operations and orchestrates required infrastructure.

### Domain / Business Logic Layer
- Contains core business concepts and logic.
- Independent of infrastructure concerns.

### Data / Persistence / Infrastructure Layer
- Handles communication with external systems (e.g., databases, file systems, messaging systems).

Calls should flow downward through the layers whenever possible.
