import React, { useCallback, useEffect, useState } from "react";
import "../assets/GamePageCss.css";
import "../features/game/component/canvas-game"; // registers custom element
import { gameController } from "../features/game/controller/game-controller";
import { Item } from "../features/game/model/Item";
import Stone from "../assets/images/stone.png";
import type { GameDto, PlayerDto } from "../shared/types/types";
import useCookieStorage from "../shared/helpers/use-cookie-storage";
import { PLAYER_LOCATION } from "../config/constants";
import "./GamePage.css";

const MOVE_PLAYER_EVENT = "MOVE_PLAYER_EVENT";

const { readCookie, createCookie } = useCookieStorage();

const INVENTORY_SLOTS = 16;

const GamePage: React.FC = () => {
  const [lootItems, setLootItems] = useState<Item[]>([]);
  const [playerX, setPlayerX] = useState(0);
  const [playerY, setPlayerY] = useState(0);
  const [playerHealth] = useState(100);
  const [inventoryOpen, setInventoryOpen] = useState(false);

  const setPlayerCoordinatesFromCookie = () => {
    const location = readCookie(PLAYER_LOCATION);
    if (!location) return;

    const coords: { x: number; y: number } = JSON.parse(location);
    setPlayerX(coords.x);
    setPlayerY(coords.y);
  };

  const movePlayer = useCallback(async (event: Event) => {
    const customEvent = event as CustomEvent<{
      playerCoordinates: { x: number; y: number };
    }>;

    const playerDto: PlayerDto =
      await gameController.movePlayerToDestination(
        customEvent.detail.playerCoordinates
      );

    if (Object.keys(playerDto).length === 0) return;

    const { x, y } = playerDto.coordinates;
    setPlayerX(x);
    setPlayerY(y);

    createCookie(
      PLAYER_LOCATION,
      JSON.stringify({ x, y }),
      1,
      "/",
      ""
    );
  }, []);

  const retrieveData = async () => {
    const status: GameDto = await gameController.getGameStatus();
    if (Object.keys(status).length === 0) return;

    const items = status.world.lootItems.map(
      (loot) => new Item(loot.x, loot.y, Stone)
    );

    items.forEach((item) => item.getImage());

    setLootItems(items);
    setPlayerCoordinatesFromCookie();
  };

  useEffect(() => {
    document.addEventListener(MOVE_PLAYER_EVENT, movePlayer);
    retrieveData();

    return () => {
      document.removeEventListener(MOVE_PLAYER_EVENT, movePlayer);
    };
  }, [movePlayer]);

  return (
    <div className="game-page">
      <div className="game-container">
        {inventoryOpen && (
          <div
            className="modal-backdrop"
            onClick={() => setInventoryOpen(false)}
          >
            <div
              className="modal"
              onClick={(e) => e.stopPropagation()}
            >
              <div className="modal-header">
                <div className="modal-title">Inventory</div>
                <button
                  className="close-btn"
                  onClick={() => setInventoryOpen(false)}
                >
                  ✕
                </button>
              </div>

              <div className="inventory-grid">
                {Array.from({ length: INVENTORY_SLOTS }).map((_, i) => (
                  <div key={i} className="inventory-slot" />
                ))}
              </div>
            </div>
          </div>
        )}

        <canvas-game
          playerX={playerX}
          playerY={playerY}
          lootItems={lootItems}
          eventName={MOVE_PLAYER_EVENT}
        />

        <div className="hud">
          <div className="health-container">
            <div className="health-label">Health</div>
            <div className="health-bar">
              <div
                className="health-fill"
                style={{ width: `${playerHealth}%` }}
              />
            </div>
          </div>

          <div className="menu-buttons">
            <button onClick={() => setInventoryOpen(true)}>
              Inventory
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default GamePage;
