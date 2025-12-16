/* eslint-disable no-magic-numbers, no-plusplus */
// eslint-disable-next-line max-lines-per-function
export default function useCookieStorage() {
  /**
   * Create a cookie
   * @param {string} name - Cookie name
   * @param {string} value - Cookie value
   * @param {number} [expiresInDays] - Number of days until the cookie expires (optional)
   * @param {string} [path] - Path for the cookie (optional)
   * @param {string} [domain] - Domain for the cookie (optional)
   * Example usage:
   * Create a cookie that expires in 7 days, with a specific path and domain
   * createCookie('yourCookieName', 'yourCookieValue', 7, '/yourpath', 'yourdomain.com');
   */
  const createCookie = (name, value, expiresInDays, path, domain) => {
    let cookieString = `${name}=${value}`;

    if (expiresInDays) {
      const expirationDate = new Date();
      expirationDate.setDate(expirationDate.getDate() + expiresInDays);
      cookieString += `; expires=${expirationDate.toUTCString()}`;
    }

    if (path) {
      cookieString += `; path=${path}`;
    }

    if (domain) {
      cookieString += `; domain=${domain}`;
    }

    document.cookie = cookieString;
  };

  /**
   *
   * @returns {Array<string>}
   */
  const getCookies = (): Array<string> => {
    const cookies = document.cookie;
    if (!cookies) {
      return [];
    }
    return cookies.split(";");
  };

  /**
   *
   * @param {string} cookieName
   * @returns {string | null}
   */
  const readCookie = (cookieName: string): string | null => {
    const cookies = getCookies();
    //if there no cookies set
    if (!cookies) {
      return null;
    }

    //loop through all cookies
    for (let index = 0; index < cookies.length; index++) {
      //create a cookie
      const cookie = cookies[index];
      //check if the given cookie name is found i the entire cookie
      const cookieFound = cookie.split(`${cookieName}=`);

      //return the cookie if found
      // lint-disable-next-line
      if (cookieFound.length === 2) {
        return cookieFound[1];
      }
    }
    return null;
  };

  /**
   *
   * @param {string} cookieName
   * @returns {void};
   */
  const deleteCookie = (cookieName: string): void => {
    const pastDate = new Date(0);
    document.cookie = `${cookieName}=; expires=${pastDate.toUTCString()}; path=/;`;
  };

  return { createCookie, readCookie, deleteCookie };
}
