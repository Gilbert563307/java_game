import { LitElement, html, css, unsafeCSS, type PropertyValues } from "lit";
import { customElement, property, state } from "lit/decorators.js";
import grass from "../../assets/images/grass.png";
import playerImage from "../../assets/images/player.png";

type spriteObject = {
  x: number;
  y: number;
  width: number;
  height: number;
};

//docs https://www.youtube.com/watch?v=Cali4HZF7TY
@customElement("moo-canvas")
export class MooCanvas extends LitElement {
  static styles = [
    css`
      :host {
        text-align: center;
      }
      canvas {
        background: url(${unsafeCSS(grass)}) no-repeat;
        background-size: cover;
        border: 2px solid black;
        image-rendering: pixelated;
      }
    `,
  ];
  playerImage: HTMLImageElement;
  drawingSurface: CanvasRenderingContext2D;
  canvasWidth: number = 1000;
  canvasHeight: number = 600;
  moveLeft: boolean = false;
  moveRight: boolean = false;
  moveUp: boolean = false;
  moveDown: boolean = false;

  Xspeed: number = 0;
  Yspeed: number = 0;

  player: spriteObject = {
    x: 0,
    y: 0,
    height: 64,
    width: 64,
  };

  loadHandler() {
    requestAnimationFrame(this.gameLoop);
  }

  //request animation frame to be updated each time
  gameLoop = () => {
    this.updateAnimation();
    requestAnimationFrame(this.gameLoop);
  };

  initGame(drawingSurface: CanvasRenderingContext2D) {
    this.drawingSurface = drawingSurface;

    const image = new Image();
    image.addEventListener("load", () => this.loadHandler(), false);
    image.src = playerImage;
    this.playerImage = image;
  }

  // create the animation loop
  updateAnimation() {
    if (this.moveUp && !this.moveDown) {
      this.Yspeed = -5;
    }

    if (this.moveDown && !this.moveUp) {
      this.Yspeed = 5;
    }
    if (this.moveLeft && !this.moveRight) {
      this.Xspeed = -5;
    }
    if (this.moveRight && !this.moveLeft) {
      this.Xspeed = 5;
    }
    if (!this.moveUp && !this.moveDown) {
      this.Yspeed = 0;
    }
    if (!this.moveLeft && !this.moveRight) {
      this.Xspeed = 0;
    }

    this.player.x += this.Xspeed;
    this.player.y += this.Yspeed;

    //boundary checks
    //stop the player  max cordinates at the canvas
    if (this.player.x < 0) {
      this.player.x = 0;
    }
    if (this.player.y < 0) {
      this.player.y = 0;
    }

    ////stop the player at the border,
    if (this.player.x + this.player.width > this.canvasWidth) {
      this.player.x = this.canvasWidth - this.player.width;
    }

    if (this.player.y + this.player.height > this.canvasHeight) {
      this.player.y = this.canvasHeight - this.player.height;
      this.Yspeed = 0
    }

    this.draw();
  }

  draw() {
    //clear and redraw
    this.drawingSurface.clearRect(0, 0, this.canvasWidth, this.canvasHeight);
    this.drawingSurface.drawImage(
      this.playerImage,
      Math.floor(this.player.x),
      Math.floor(this.player.y),
      this.player.height,
      this.player.width
    );
  }

  handleKeyPress(event: Event, on: boolean) {
    switch (event.key) {
      case "ArrowUp":
        this.moveUp = on;
        break;

      case "ArrowDown":
        this.moveDown = on;
        break;

      case "ArrowLeft":
        this.moveLeft = on;
        break;
      case "ArrowRight":
        this.moveRight = on;
        break;

      default:
        break;
    }
    this.updateAnimation();
  }

  removeEvents(e: Event){
    this.handleKeyPress(e, false);
    console.log(this.player)
  }

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

  render() {
    return html`
      <section
        tabindex="0"
        @keydown=${(e: Event) => this.handleKeyPress(e, true)}
        @keyup=${(e: Event) => this.removeEvents(e)}
      >
        <h1>Move the player with the keyboard and grab the items</h1>
        <canvas
          width="${this.canvasWidth}"
          height="${this.canvasHeight}"
        ></canvas>
      </section>
    `;
  }
}

declare global {
  interface HTMLElementTagNameMap {
    "moo-canvas": MooCanvas;
  }
}
