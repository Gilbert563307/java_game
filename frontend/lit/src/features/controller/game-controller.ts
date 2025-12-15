import { gameService } from "../service/game-service";

class GameController {
  // Create a game
  async createGame(worldName: string, username: string) {
    try {
      const game = await gameService.createGame(worldName, username);
      console.log('Game created:', game);
      return game;
    } catch (error) {
      console.error('Error creating game:', error);
      throw error;
    }
  }

  // Get game status
  async getGameStatus(gameId: number) {
    try {
      const status = await gameService.getGameStatus(gameId);
      console.log('Game status:', status);
      return status;
    } catch (error) {
      console.error('Error getting game status:', error);
      throw error;
    }
  }

  // Join a game
  async joinGame(gameId: number, username: string) {
    try {
      const player = await gameService.joinGame(gameId, username);
      console.log('Joined game:', player);
      return player;
    } catch (error) {
      console.error('Error joining game:', error);
      throw error;
    }
  }

  // Move a player
  async movePlayer(gameId: number, playerId: number, direction: string) {
    try {
      const newPosition = await gameService.movePlayer(gameId, playerId, direction);
      console.log('Player moved:', newPosition);
      return newPosition;
    } catch (error) {
      console.error('Error moving player:', error);
      throw error;
    }
  }

  // Leave a game
  async leaveGame(gameId: number, playerId: number) {
    try {
      const result = await gameService.leaveGame(gameId, playerId);
      console.log('Left game:', result);
      return result;
    } catch (error) {
      console.error('Error leaving game:', error);
      throw error;
    }
  }

  // Quit a game
  async quitGame(gameId: number) {
    try {
      const result = await gameService.quitGame(gameId);
      console.log('Game quit:', result);
      return result;
    } catch (error) {
      console.error('Error quitting game:', error);
      throw error;
    }
  }

  // Get a player
  async getPlayer(gameId: number, playerId: number) {
    try {
      const player = await gameService.getPlayer(gameId, playerId);
      console.log('Player info:', player);
      return player;
    } catch (error) {
      console.error('Error getting player:', error);
      throw error;
    }
  }
}

const gameController = new GameController();
export { gameController };
