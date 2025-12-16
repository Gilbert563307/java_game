import { ALERT_TYPES, GAME_ID } from "../../../config/constants";
import useCookieStorage from "../../../shared/helpers/use-cookie-storage";
import { notificationSubjectService } from "../../../shared/observers/notification-subject-service";
import type { GameDto, PlayerDto } from "../../../shared/types/types";
import { gameService } from "../service/game-service";

const { createCookie, readCookie } = useCookieStorage();
class GameController {
  #setMessageToUser(message: string) {
    if (message != "" || message != undefined) {
      //clear old data before setting new data
      notificationSubjectService.clear();
      const data = { message: message, type: ALERT_TYPES.DANGER };
      notificationSubjectService.addData(data);
    }
  }

  // Create a game
  async createGame(payload: {
    worldName: string;
    username: string;
  }): Promise<GameDto | Object> {
    try {
      if (payload.username === "" || payload.worldName === "") {
        this.#setMessageToUser("Your username or world name cannot be empty");
        return {};
      }

      const gameDto: GameDto = await gameService.createGame(payload);
      createCookie(GAME_ID, String(gameDto.id), 1, "/", "");
      this.createGame(PLAYER_ID, String(gameDto.))
      return gameDto;
    } catch (error: unknown) {
      this.#setMessageToUser(error.message);
      return {};
    }
  }

  // Get game status
  async getGameStatus(): Promise<GameDto | Object> {
    try {
      const gameId: string | null = readCookie(GAME_ID);
      if(gameId == null){
        this.#setMessageToUser("Failed to fetch game status");
        return {};
      }
      return await gameService.getGameStatus(gameId);
    } catch (error: unknown) {
      this.#setMessageToUser(error.message);
      return {};
    }
  }

  // Join a game
  async joinGame(gameId: number, username: string): Promise<boolean> {
    try {
      await gameService.joinGame(gameId, username);
      return true;
    } catch (error: unknown) {
      this.#setMessageToUser(error.message);
      return false;
    }
  }

  // Move a player
  async movePlayer(
    gameId: number,
    playerId: number,
    direction: string
  ): Promise<PlayerDto | Object> {
    try {
      return await gameService.movePlayer(gameId, playerId, direction);
    } catch (error) {
      this.#setMessageToUser(error.message);
      return {};
    }
  }

  // Leave a game
  async leaveGame(gameId: number, playerId: number): Promise<boolean> {
    try {
      await gameService.leaveGame(gameId, playerId);
      return true;
    } catch (error: unknown) {
      this.#setMessageToUser(error.message);
      return false;
    }
  }

  // Quit a game
  async quitGame(gameId: number): Promise<boolean> {
    try {
      await gameService.quitGame(gameId);
      return true;
    } catch (error: unknown) {
      this.#setMessageToUser(error.message);
      return false;
    }
  }

  // Get a player
  async getPlayer(
    gameId: number,
    playerId: number
  ): Promise<PlayerDto | Object> {
    try {
      return await gameService.getPlayer(gameId, playerId);
    } catch (error: unknown) {
      this.#setMessageToUser(error.message);
      return {};
    }
  }
}

const gameController = new GameController();
export { gameController };
