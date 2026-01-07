import type { GameDto, PlayerDto } from "../../../shared/types/types";

class GameService {
  
  baseUrl: string;
  constructor() {
    this.baseUrl = import.meta.env.VITE_GAME_API_URL;
  }

  // Create a game
  async createGame(payload: {
    worldName: string;
    username: string;
  }): Promise<GameDto> {
    const response = await fetch(`${this.baseUrl}/game`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(payload),
    });
    const data = await response.json();

    if (!response.ok) {
      throw new Error(data?.message ?? "Failed to create game");
    }
    return data;
  }


  //move player to destination 
  async movePlayerToDestination(gameId: number, playerId: number, payload: { x: number, y: number} ): Promise<PlayerDto> {
     const response = await fetch(
      `${this.baseUrl}/game/${gameId}/players/${playerId}/coordinates`,
      {
        method: "PATCH",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload),
      }
    );
    return response.json();
  }


  // Get game status
  async getGameStatus(gameId: number): Promise<GameDto> {
    const response = await fetch(`${this.baseUrl}/game/${gameId}`, {
      method: "GET",
      headers: { Accept: "application/json" },
    });
    const data = await response.json();

    if (!response.ok) {
      throw new Error(data?.message ?? "Failed to get game status");
    }
    return data;
  }

  // Join game
  async joinGame(gameId: number, username: string): Promise<void> {
    const response = await fetch(`${this.baseUrl}/game/${gameId}/players`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ username }),
    });
    return response.json();
  }

  // Get player in game
  async getPlayer(
    gameId: number,
    playerId: number
  ): Promise<PlayerDto | Object> {
    const response = await fetch(
      `${this.baseUrl}/game/${gameId}/player/${playerId}`,
      {
        method: "GET",
        headers: { Accept: "application/json" },
      }
    );
    const data = await response.json();

    if (!response.ok) {
      throw new Error(data?.message ?? "Failed to get player status");
    }
    return data;
  }

  // Move player
  async movePlayer(
    gameId: number,
    playerId: number,
    direction: string
  ): Promise<PlayerDto> {
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
  async leaveGame(gameId: number, playerId: number): Promise<void> {
    const response = await fetch(
      `${this.baseUrl}/game/${gameId}/players/${playerId}`,
      {
        method: "DELETE",
      }
    );
    return response.json();
  }

  // Quit game
  async quitGame(gameId: number): Promise<void> {
    const response = await fetch(`${this.baseUrl}/game/${gameId}`, {
      method: "DELETE",
    });
    return response.json();
  }
}

const gameService = new GameService();
export { gameService };
