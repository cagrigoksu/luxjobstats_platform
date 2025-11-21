using AuthService.Data;
using AuthService.Models;
using AuthService.Services;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;

namespace AuthService.Controllers;


[Route("auth")]
[ApiController]
public class AuthController : ControllerBase
{
    private readonly UserManager<User> _userManager;
    private readonly SignInManager<User> _signInManager;
    private readonly JwtService _jwtService;
    private readonly RefreshTokenService _refreshTokenService;


    public AuthController(
        UserManager<User> userManager,
        SignInManager<User> signInManager,
        JwtService jwtService,
        RefreshTokenService refreshTokenService)
    {
        _userManager = userManager;
        _signInManager = signInManager;
        _jwtService = jwtService;
        _refreshTokenService = refreshTokenService;
    }

    [HttpPost("register")]
    public async Task<IActionResult> Register(RegisterDto dto)
    {
        var user = new User
        {
            UserName = dto.Email,
            Email = dto.Email
        };

        var result = await _userManager.CreateAsync(user, dto.Password);

        if (!result.Succeeded)
            return BadRequest(result.Errors);

        return Ok(new { message = "User registered." });
    }

    [HttpPost("login")]
    public async Task<IActionResult> Login(LoginDto dto)
    {
        var user = await _userManager.FindByEmailAsync(dto.Email);

        if (user == null)
            return Unauthorized(new { message = "Invalid login" });

        var passwordCheck = await _signInManager.CheckPasswordSignInAsync(user, dto.Password, false);

        if (!passwordCheck.Succeeded)
            return Unauthorized(new { message = "Invalid password" });

        var accessToken = _jwtService.GenerateToken(user);
        var refreshToken = await _refreshTokenService.CreateRefreshToken(user.Id);

        return Ok(new
        {
            message = "Login successful",
            accessToken = accessToken,
            refreshToken = refreshToken.Token
        });
    }


    [HttpPost("refresh")]
    public async Task<IActionResult> Refresh(RefreshRequest req)
    {
        var existingToken = await _refreshTokenService.GetValidToken(req.RefreshToken);
        if (existingToken == null)
            return Unauthorized(new { message = "Invalid refresh token" });

        // revoke old token
        await _refreshTokenService.RevokeToken(existingToken);

        // issue new access and refresh tkn
        var accessToken = _jwtService.GenerateToken(existingToken.User);
        var newRefresh = await _refreshTokenService.CreateRefreshToken(existingToken.UserId);

        return Ok(new
        {
            accessToken = accessToken,
            refreshToken = newRefresh.Token
        });
    }


}

public record RegisterDto(string Email, string Password);
public record LoginDto(string Email, string Password);
public record RefreshRequest(string RefreshToken);

