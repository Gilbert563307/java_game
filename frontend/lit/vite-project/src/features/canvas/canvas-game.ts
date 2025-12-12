import { LitElement, html, css, unsafeCSS, type PropertyValues } from "lit";
import { customElement, property, state } from "lit/decorators.js";

import grass from "../../assets/images/grass.png";
import playerImage from "../../assets/images/player.png";
import { PlayerModel } from "./model/player-model";

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

  //   @property({ type: Object })
  player: PlayerModel = new PlayerModel(0, 0, 64, 64, playerImage);

  drawingSurface: CanvasRenderingContext2D;
  canvasWidth: number = 1000;
  canvasHeight: number = 600;

  //after component is loaded
  protected firstUpdated(_changedProperties: PropertyValues): void {
    const canvas: HTMLCanvasElement = this.shadowRoot?.querySelector("canvas");
    const drawingSurface: CanvasRenderingContext2D = canvas?.getContext("2d");
    if (!drawingSurface) {
      return;
    }
    this.initGame(drawingSurface);

    // Automatically focus the section so keyboard works
    const section = this.shadowRoot?.querySelector("section");
    section?.focus();
  }

  initGame(drawingSurface: CanvasRenderingContext2D) {
    this.drawingSurface = drawingSurface;
    this.updateCharacter();
  }

  draw() {
    //wait for image to load
    //https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/drawImage
    this.player.getImage().onload = () => {
      //clear and redraw
      this.drawingSurface.clearRect(
        this.player.getX(),
        this.player.getY(),
        this.canvasWidth,
        this.canvasHeight
      );
      this.drawingSurface.drawImage(
        this.player.getImage(),
        Math.floor(this.player.getX()),
        Math.floor(this.player.getY()),
        this.player.getHeight(),
        this.player.getWidth()
      );
    };

    console.log(this.drawingSurface);
    console.log(this.player);
  }

  updateCharacter() {
    this.draw();
  }

  handleKeyPress(event: Event, on: boolean) {
    switch (event.key) {
      case "ArrowUp":
        this.player.move("UP");
        break;

      case "ArrowDown":
        break;

      case "ArrowLeft":
        break;
      case "ArrowRight":
        break;

      default:
        break;
    }
    this.updateCharacter();
  }

  render() {
    return html`
      <section tabindex="0">
        <canvas
          @keydown=${(e: Event) => this.handleKeyPress(e, true)}
          width="${this.canvasWidth}"
          height="${this.canvasHeight}"
        ></canvas>
      </section>
    `;
  }
}

declare global {
  interface HTMLElementTagNameMap {
    "canvas-game": CanvasGame;
  }
}
