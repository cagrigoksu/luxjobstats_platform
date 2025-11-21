using AuthService.Data;

namespace AuthService.Models;

public class RefreshToken
{
    public int Id {get; set;}
    public string Token {get;set;}
    public string UserId {get;set;}
    public DateTime ExpiresAt {get;set;}
    public bool IsRevoked {get;set;} = false;
    public User User {get;set;}


}