# Questions

Here we have 3 questions related to the code base for you to answer. It is not about right or wrong, but more about what's the reasoning behind your decisions.

1. In this code base, we have some different implementation strategies when it comes to database access layer and manipulation. If you would maintain this code base, would you refactor any of those? Why?

**Answer:**
```txt
Yes, I would refactor the codebase to use a consistent strategy. Currently, it uses both the Active Record pattern (e.g., in `Store.java` using `PanacheEntity`) and the Repository pattern (e.g., in `WarehouseRepository.java` using `PanacheRepository`).
I would refactor towards the Repository pattern. The Repository pattern separates data access logic from the domain models, making the domain models cleaner, easier to unit test, and more compliant with the Single Responsibility Principle. Active Record can tightly couple the database and the domain, which becomes harder to manage in complex business logic.
```
----
2. When it comes to API spec and endpoints handlers, we have an Open API yaml file for the `Warehouse` API from which we generate code, but for the other endpoints - `Product` and `Store` - we just coded directly everything. What would be your thoughts about what are the pros and cons of each approach and what would be your choice?

**Answer:**
```txt
API-First (OpenAPI yaml driven):
Pros: The API contract is the absolute source of truth. It allows frontend and backend teams to work in parallel, simplifies generating client/server code, and guarantees the documentation is always accurate.
Cons: It introduces a slightly more complex build process and requires developers to learn OpenAPI syntax.

Code-First (Coding directly):
Pros: Faster to start and prototype. Developers write standard Java code without worrying about managing a YAML specification.
Cons: The documentation is often an afterthought and can easily drift from the actual implementation. It's harder to manage API versioning and contracts cleanly.

Choice: I would choose the API-First approach. It acts as centralized documentation and prevents miscommunication between integrating systems.
```
----
3. Given the need to balance thorough testing with time and resource constraints, how would you prioritize and implement tests for this project? Which types of tests would you focus on, and how would you ensure test coverage remains effective over time?

**Answer:**
```txt
1. Prioritization: I would focus first on unit testing the core domain logic and Use Cases (e.g., `CreateWarehouseUseCase`), as these contain complex constraints (capacity, maximum sizes) and the cost of bugs here is high.
2. Integration Testing: Next, I would add REST API integration tests using Quarkus `@QuarkusTest` to ensure endpoints return correct HTTP codes and persist data accurately in a test database like H2 or Testcontainers.
3. Effectiveness over time: I would automate this in a CI/CD pipeline (e.g., GitHub Actions) and use a tool like JaCoCo configured to fail the build if code coverage falls below a strict threshold (e.g., 80%).
```