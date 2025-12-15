class GameService {
  baseUrl: string;
  constructor() {
    this.baseUrl = import.meta.env.VITE_GAME_API_URL;
  }

  // Create a game
  async createGame(worldName: string, username: string) {
    const response = await fetch(`${this.baseUrl}/game`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ worldName, username }),
    });
    return response.json();
  }

  // Get game status
  async getGameStatus(gameId: number) {
    const response = await fetch(`${this.baseUrl}/game/${gameId}`, {
      method: "GET",
      headers: { Accept: "application/json" },
    });
    return response.json();
  }

  // Join game
  async joinGame(gameId: number, username: string) {
    const response = await fetch(`${this.baseUrl}/game/${gameId}/players`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ username }),
    });
    return response.json();
  }

  // Get player in game
  async getPlayer(gameId: number, playerId: number) {
    const response = await fetch(
      `${this.baseUrl}/game/${gameId}/player/${playerId}`,
      {
        method: "GET",
        headers: { Accept: "application/json" },
      }
    );
    return response.json();
  }

  // Move player
  async movePlayer(gameId: number, playerId: number, direction: string) {
    const response = await fetch(
      `${this.baseUrl}/game/${gameId}/players/${playerId}/position`,
      {
        method: "PATCH",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ direction }),
      }
    );
    return response.json();
  }

  // Leave game
  async leaveGame(gameId: number, playerId: number) {
    const response = await fetch(
      `${this.baseUrl}/game/${gameId}/players/${playerId}`,
      {
        method: "DELETE",
      }
    );
    return response.json();
  }

  // Quit game
  async quitGame(gameId: number) {
    const response = await fetch(`${this.baseUrl}/game/${gameId}`, {
      method: "DELETE",
    });
    return response.json();
  }
}

const gameService = new GameService();
export { gameService };
