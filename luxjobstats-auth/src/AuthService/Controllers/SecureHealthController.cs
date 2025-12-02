using AuthService.Data;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

namespace AuthService.Controllers;

[ApiController]
[Route("app-health")]
public class SecureHealthController : ControllerBase
{
    private readonly ApplicationDbContext _dbContext;

    public SecureHealthController(ApplicationDbContext dbContext)
    {
        _dbContext = dbContext;
    }

    [Authorize]
    [HttpGet("jwtSecurityTest")]
    public IActionResult JwtSecurityTest()
    {
        return Ok(new { message = "AuthService is running, Security Test passed using Jwt." });
    }

    // db connection test
    [HttpGet("dbConnectionTest")]
    public async Task<IActionResult> DbConnectionTest()
    {
        try
        {
            var conn = await _dbContext.Database.CanConnectAsync();

            return Ok(new
            {
                database = conn ? "Conn. OK" : "Conn. ERROR"
            });
        }
        catch (Exception ex)
        {
            return StatusCode(500, new
            {
                database = "error",
                error = ex.Message
            });
        }
    }
}
