import { LitElement, html, css, unsafeCSS, type PropertyValues } from "lit";
import { customElement, property, state } from "lit/decorators.js";
import grass from "../../../assets/images/grass.png";
import playerImage from "../../../assets/images/player.png";
import { Player } from "../model/player";
import type { Item } from "../model/Item";

@customElement("canvas-game")
export class CanvasGame extends LitElement {
  static styles = [
    css`
      canvas {
        background: url(${unsafeCSS(grass)}) no-repeat;
        background-size: cover;
        border: 2px solid black;
        image-rendering: pixelated;
      }
    `,
  ];

  scope: Document = document;

  @property({ type: Array })
  lootItems: Array<Item> = [];

  @property({type: String})
  eventName: string ="";

  canvasWidth: number = 1000;
  canvasHeight: number = 600;
  player: Player = new Player(
    0,
    0,
    64,
    64,
    playerImage,
    this.canvasWidth,
    this.canvasHeight
  );

  ctx: CanvasRenderingContext2D;

  //after component is loaded
  protected firstUpdated(_changedProperties: PropertyValues): void {
    const canvas: HTMLCanvasElement = this.shadowRoot?.querySelector("canvas");
    canvas.width = this.canvasWidth;
    canvas.height = this.canvasHeight;
    const drawingSurface: CanvasRenderingContext2D = canvas?.getContext("2d");
    if (!drawingSurface) {
      return;
    }
    this.initGame(drawingSurface);

    // Automatically focus the section so keyboard works
    const section: HTMLElement = this.shadowRoot?.querySelector("section");
    section?.focus();
  }

  initGame(drawingSurface: CanvasRenderingContext2D) {
    this.ctx = drawingSurface;

    //wait for image to load
    const playerImage = this.player.getImage();
    playerImage.onload = () => {
      this.updateCharacter();
    };
    // requestAnimationFrame(this.updateCharacter);
  }

  draw() {
    //https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/drawImage
    //clear and redraw
    this.ctx.clearRect(0, 0, this.canvasWidth, this.canvasHeight);

    this.ctx.drawImage(
      this.player.getImage(),
      Math.floor(this.player.getX()),
      Math.floor(this.player.getY()),
      this.player.getWidth(),
      this.player.getHeight()
    );

    // draw loot items
    this.lootItems.forEach((item) => {
      this.ctx.drawImage(
        item.getImage(),
        item.getX(),
        item.getY(),
        item.getWidth(),
        item.getHeight()
      );
    });
  }

  //auto bind this method to this lass
  updateCharacter = () => {
    this.draw();
  };

  movePlayer() {
    const movePlayerEvent: CustomEvent = new CustomEvent(this.eventName, {
      detail: {
        playerCoordinates: this.player.getCoordinates(),
      },
      bubbles: true,
      composed: true,
    });
    this.scope.dispatchEvent(movePlayerEvent);
  }

  handleKeyPress(event: KeyboardEvent, moving: boolean) {
    switch (event.key) {
      case "ArrowUp":
        this.player.move("UP");
        break;
      case "ArrowDown":
        this.player.move("DOWN");
        break;

      case "ArrowLeft":
        this.player.move("LEFT");
        break;
      case "ArrowRight":
        this.player.move("RIGHT");
        break;

      default:
        break;
    }

    this.updateCharacter();
    if (moving === false) {
      this.movePlayer();
    }
  }

  render() {
    return html`
      <section
        tabindex="0"
        @keydown=${(e: KeyboardEvent) => this.handleKeyPress(e, true)}
        @keyup=${(e: KeyboardEvent) => this.handleKeyPress(e, false)}
      >
        <canvas></canvas>
      </section>
    `;
  }
}

declare global {
  interface HTMLElementTagNameMap {
    "canvas-game": CanvasGame;
  }
}
