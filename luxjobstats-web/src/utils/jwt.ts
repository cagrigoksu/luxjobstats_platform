export function decodeJwt(token: string) {
  try {

    const payload = token.split(".")[1];
    const json = atob(payload); // decode base64
    return JSON.parse(json);
  } 
  catch {
    return null;
  }
}
