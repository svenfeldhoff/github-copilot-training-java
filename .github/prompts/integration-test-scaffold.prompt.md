# Integration Test Scaffold

Generate a complete `@SpringBootTest` + `MockMvc` integration test class for the attached controller or endpoint. Follow the rules below exactly.

## Rules

1. Use `@SpringBootTest` + `@AutoConfigureMockMvc` on the test class.
2. Inject `MockMvc` via constructor injection.
3. Name the test class `<ControllerName>IntegrationTest`.
4. Generate exactly three test methods per endpoint:
   - **Success path** — valid request, assert correct HTTP status and every field in the JSON response body.
   - **Validation failure path** — invalid or missing required field, assert HTTP 400.
   - **Error path** — assert HTTP 404 (resource not found) or HTTP 409 (conflict) where the endpoint contract requires it.
5. Use `MockMvcRequestBuilders` and `MockMvcResultMatchers.jsonPath` for all assertions.
6. Do not use `@MockBean` unless the endpoint requires an external service call outside this codebase.
7. Add a one-line Javadoc comment on each test method describing what it verifies.

## Output format

Return only the complete Java class. Do not include explanatory prose outside the class.

