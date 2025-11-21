using AuthService.Data;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace AuthService.Controllers;

[ApiController]
[Route("api/[controller]")]
public class HealthController : ControllerBase
{
    private readonly ApplicationDbContext _dbContext;

    public HealthController(ApplicationDbContext dbContext)
    {
        _dbContext = dbContext;
    }

    [HttpGet("hello")]
    public IActionResult Hello()
    {
        return Ok(new { message = "AuthService is running." });
    }

    // db connection test
    [HttpGet("db")]
    public async Task<IActionResult> Db()
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
