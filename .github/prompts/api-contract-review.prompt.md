# API Contract Review

Review the attached endpoint against the following project standards and return findings ordered by severity (Critical -> Warning -> Note). Include exact file and line references for every finding.

## Standards to enforce

1. **JSON responses only** — business endpoints must return a JSON object or array, never plain text or `void`.
2. **Controller → Service separation** — no business logic or repository calls inside the controller.
3. **DTO validation** — every request body must be annotated with `@Valid` on the controller parameter and use Jakarta Validation constraints (`@NotBlank`, `@NotNull`, `@Size`, etc.) on the DTO fields.
4. **Constructor injection** — no `@Autowired` field injection; use constructor injection in all Spring components.
5. **Unit tests** — every public service method must have a corresponding unit test covering at least the success path.
6. **Integration tests** — every controller endpoint must have an integration test (`@SpringBootTest` + `MockMvc`) covering:
   - Success path (correct status code + response body).
   - Validation failure path (400 response for invalid input).
   - Error path (404 or 409 where applicable).
7. **Validation-failure test** — if any controller parameter carries `@Valid`, there must be an explicit integration test asserting a 400 response with an invalid payload.

## Output format

For each finding:
- **Severity**: Critical / Warning / Note
- **File**: relative path
- **Line**: line number
- **Issue**: one-line description
- **Fix**: one-line suggested fix

