import { LitElement, html, css } from "lit";
import { customElement, property } from "lit/decorators.js";
import "../features/game/component/canvas-game.ts";
import { gameController } from "../features/game/controller/game-controller.ts";
import { Item } from "../features/game/model/Item";
import Stone from "../assets/images/stone.png";

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

      /* Minecraft-style dirt background */
      background-color: #3a2f25;
      background-image: linear-gradient(
          90deg,
          rgba(0, 0, 0, 0.08) 50%,
          transparent 50%
        ),
        linear-gradient(rgba(0, 0, 0, 0.08) 50%, transparent 50%),
        radial-gradient(rgba(0, 0, 0, 0.2), transparent 70%);
      background-size: 32px 32px, 32px 32px, 100% 100%;
      background-position: 0 0, 0 0, center;
    }

    canvas-game {
      z-index: 1;
    }
  `;

  eventName: string = MOVE_PLAYER_EVENT;
  scope: Document = document;

  @property({ type: Array })
  lootItems: Array<Item> = [];


  movePlayer(event: Event){
    const playerCoordinates: {x: number, y: number} = event.detail.playerCoordinates;
    //TODO FIX
    // gameController.movePlayer(playerCoordinates)
  }

  connectedCallback(): void {
    super.connectedCallback();
    this.scope.addEventListener(this.eventName, this.movePlayer);
    this.retrieveData();
  }

  disconnectedCallback(): void {
    super.disconnectedCallback();
    this.scope.removeEventListener(this.eventName, this.movePlayer)
  }

  async retrieveData() {
    const status = await gameController.getGameStatus();
    if (Object.keys(status).length === 0) return;
    this.lootItems = status.world.lootItems;

    // preload loot item images
    this.lootItems = this.lootItems.map(
      (loot) => new Item(loot.x, loot.y, Stone)
    );

    this.lootItems.forEach((item) => item.getImage());
  }

  render() {
    return html`<canvas-game
      .lootItems=${this.lootItems}
      eventName=${this.eventName}
    ></canvas-game>`;
  }
}

declare global {
  interface HTMLElementTagNameMap {
    "game-page": GamePage;
  }
}
