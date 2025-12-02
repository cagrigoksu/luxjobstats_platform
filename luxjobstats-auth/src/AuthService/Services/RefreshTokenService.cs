using AuthService.Data;
using AuthService.Models;
using Microsoft.EntityFrameworkCore;

namespace AuthService.Services;

public class RefreshTokenService
{
    private readonly ApplicationDbContext _db;

    public RefreshTokenService(ApplicationDbContext db)
    {
        _db = db;
    }

    public async Task<RefreshToken> CreateRefreshToken(string userId)
    {
        var token = new RefreshToken
        {
            UserId = userId,
            Token = Guid.NewGuid().ToString(),
            ExpiresAt = DateTime.UtcNow.AddDays(7)
        };

        _db.RefreshTokens.Add(token);
        await _db.SaveChangesAsync();

        return token;
    }

    public async Task<RefreshToken> GetValidToken(string token)
    {
        return await _db.RefreshTokens
            .Include(rt => rt.User)
            .FirstOrDefaultAsync(rt =>
                rt.Token == token &&
                rt.IsRevoked == false &&
                rt.ExpiresAt > DateTime.UtcNow
            );
    }

    public async Task RevokeToken(RefreshToken token)
    {
        token.IsRevoked = true;
        await _db.SaveChangesAsync();
    }

}
