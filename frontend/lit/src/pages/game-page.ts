import { LitElement, html, css } from "lit";
import { customElement, property } from "lit/decorators.js";
import "../features/game/component/canvas-game.ts";
import { gameController } from "../features/game/controller/game-controller.ts";
import { Item } from "../features/game/model/Item";
import Stone from "../assets/images/stone.png";
import type { GameDto, PlayerDto } from "../shared/types/types.ts";
import useCookieStorage from "../shared/helpers/use-cookie-storage.ts";
import { PLAYER_LOCATION } from "../config/constants.ts";

const { readCookie, createCookie } = useCookieStorage();
const MOVE_PLAYER_EVENT: string = "MOVE_PLAYER_EVENT";
@customElement("game-page")
export class GamePage extends LitElement {
  static styles = css`
    :host {
      min-height: 100vh;
      width: 100%;
      display: flex;
      justify-content: center;
      align-items: center;

      background-color: #3a2f25;
      background-image: linear-gradient(90deg, rgba(0, 0, 0, 0.08) 50%, transparent 50%),
        linear-gradient(rgba(0, 0, 0, 0.08) 50%, transparent 50%), radial-gradient(rgba(0, 0, 0, 0.2), transparent 70%);
      background-size: 32px 32px, 32px 32px, 100% 100%;
      background-position: 0 0, 0 0, center;
    }

    .game-container {
      position: relative;
    }

    canvas-game {
      z-index: 1;
      display: block;
    }

    /* ---------- HUD ---------- */
    .hud {
      position: absolute;
      inset: 0;
      pointer-events: none; /* allow clicks through except buttons */
      z-index: 2;
    }

    /* Health bar */
    .health-container {
      position: absolute;
      top: 16px;
      left: 16px;
      width: 220px;
    }

    .health-label {
      font-size: 14px;
      margin-bottom: 4px;
      color: black;
      font-weight: 600;
    }

    .health-bar {
      width: 100%;
      height: 18px;
      border: 2px solid black;
      background: #5b5b5b;
    }

    .health-fill {
      height: 100%;
      width: 100%;
      background: #39d12f;
    }

    /* Buttons */
    .menu-buttons {
      position: absolute;
      top: 16px;
      right: 16px;
      display: flex;
      flex-direction: column;
      gap: 10px;
      pointer-events: auto;
    }

    .menu-buttons button {
      background: #8f8f8f;
      color: white;
      border: none;
      padding: 10px 16px;
      border-radius: 8px;
      font-size: 14px;
      cursor: pointer;
    }

    .menu-buttons button:hover {
      background: #7a7a7a;
    }

    /* ---------- MODAL ---------- */
    .modal-backdrop {
      position: absolute;
      inset: 0;
      background: rgba(0, 0, 0, 0.6);
      z-index: 10;
      display: flex;
      justify-content: center;
      align-items: center;
      pointer-events: auto;
    }

    .modal {
      background: #c6c6c6;
      border: 4px solid #4a4a4a;
      width: 400px;
      padding: 16px;
      border-radius: 6px;
      box-shadow: 0 8px 20px rgba(0, 0, 0, 0.5);
    }

    .modal-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 12px;
    }

    .modal-title {
      font-size: 18px;
      font-weight: bold;
    }

    .close-btn {
      background: #8f8f8f;
      border: none;
      color: white;
      padding: 4px 10px;
      cursor: pointer;
      border-radius: 4px;
    }

    .close-btn:hover {
      background: #7a7a7a;
    }

    .inventory-grid {
      display: grid;
      grid-template-columns: repeat(4, 1fr);
      gap: 8px;
    }

    .inventory-slot {
      width: 64px;
      height: 64px;
      background: #9e9e9e;
      border: 2px solid #5a5a5a;
    }
  `;

  eventName: string = MOVE_PLAYER_EVENT;
  scope: Document = document;

  @property({ type: Array })
  lootItems: Array<Item> = [];

  @property({ type: Number })
  playerX: number = 0;

  @property({ type: Number })
  playerY: number = 0;

  @property({ type: Number })
  playerHealth = 100;

  @property({ type: Boolean })
  inventoryOpen = false;

  private boundMovePlayer = this.movePlayer.bind(this);

  async movePlayer(event: Event) {
    const playerCoordinates: { x: number; y: number } = event.detail.playerCoordinates;

    const playerDto: PlayerDto = await gameController.movePlayerToDestination(playerCoordinates);

    if (Object.keys(playerDto).length > 0) {
      this.playerX = playerDto.coordinates.x;
      this.playerY = playerDto.coordinates.y;
      createCookie(PLAYER_LOCATION, JSON.stringify({ x: this.playerX, y: this.playerY }), 1, "/", "");
    }
  }

  connectedCallback(): void {
    super.connectedCallback();
    this.scope.addEventListener(this.eventName, this.boundMovePlayer);
    this.retrieveData();
  }

  disconnectedCallback(): void {
    super.disconnectedCallback();
    this.scope.removeEventListener(this.eventName, this.boundMovePlayer);
  }

  setPlayerCoordinates() {
    const playerLocation = readCookie(PLAYER_LOCATION);
    if (playerLocation != null) {
      const coordinates: { x: number; y: number } = JSON.parse(playerLocation);
      this.playerX = coordinates.x;
      this.playerY = coordinates.y;
    }
  }

  async retrieveData() {
    const status: GameDto = await gameController.getGameStatus();
    if (Object.keys(status).length === 0) return;
    this.lootItems = status.world.lootItems;

    // preload loot item images
    this.lootItems = this.lootItems.map((loot) => new Item(loot.x, loot.y, Stone));

    this.lootItems.forEach((item) => item.getImage());
    this.setPlayerCoordinates();
  }

  openInventory() {
    this.inventoryOpen = true;
  }

  closeInventory() {
    this.inventoryOpen = false;
  }

  render() {
    return html`
      <div class="game-container">
        ${this.inventoryOpen
          ? html`
              <div class="modal-backdrop" @click=${this.closeInventory}>
                <div class="modal" @click=${(e: Event) => e.stopPropagation()}>
                  <div class="modal-header">
                    <div class="modal-title">Inventory</div>
                    <button class="close-btn" @click=${this.closeInventory}>✕</button>
                  </div>

                  <div class="inventory-grid">
                    ${Array.from({ length: 16 }).map(() => html`<div class="inventory-slot"></div>`)}
                  </div>
                </div>
              </div>
            `
          : null}

        <canvas-game
          playerX=${this.playerX}
          playerY=${this.playerY}
          .lootItems=${this.lootItems}
          eventName=${this.eventName}
        ></canvas-game>

        <div class="hud">
          <!-- Health bar -->
          <div class="health-container">
            <div class="health-label">Health</div>
            <div class="health-bar">
              <div class="health-fill" style="width: ${this.playerHealth}%"></div>
            </div>
          </div>

          <!-- Menu buttons -->
          <div class="menu-buttons">
            <button @click=${this.openInventory}>Inventory</button>
            <!--  <button @click=${this.openOptions}>Options</button> -->
          </div>
        </div>
      </div>
    `;
  }
}

declare global {
  interface HTMLElementTagNameMap {
    "game-page": GamePage;
  }
}
