import {
  LitElement,
  html,
  css,
  type TemplateResult,
  unsafeCSS,
  type PropertyValues,
} from "lit";
import { customElement, property, state } from "lit/decorators.js";
import { COLUMNS, ROWS } from "../../config/constants";
import grass from "../../assets/images/grass.png";

type Item = {
  x: number;
  y: number;
  size: number;
  color: string;
};

@customElement("canvas-grid")
export class Grid extends LitElement {
  @property({ type: Number })
  rows: number = ROWS;
  columns: number = COLUMNS;

  static styles = [
    css`
      #canvas {
        width: 1000px;
        height: 700px;
        image-rendering: pixelated;
        border: 1px solid black;
      }
    `,
  ];

  canvas: HTMLCanvasElement;
  x: number = 15;
  y: number = 10;
  items: Item[] = [];

  // fillRect(x, y, width, height)
  // Draws a filled rectangle.

  //     strokeRect(x, y, width, height)
  // Draws a rectangular outline.

  // clearRect(x, y, width, height)
  // Clears the specified rectangular area, making it fully transparent.

  protected firstUpdated(_changedProperties: PropertyValues): void {
    const canvas = this.shadowRoot?.querySelector(
      "#canvas"
    ) as HTMLCanvasElement;
    if (!canvas) return;

    this.canvas = canvas;
    this.setItemsToCanvas(8);
  }

  setItemsToCanvas(count = 5) {
    this.items = [];

    for (let i = 0; i < count; i++) {
      this.items.push({
        x: Math.floor(Math.random() * (this.canvas.width - 20)),
        y: Math.floor(Math.random() * (this.canvas.height - 20)),
        size: 20,
        color: "brown",
      });
    }

    this.draw();
  }

  drawItems(ctx: CanvasRenderingContext2D) {
    for (const item of this.items) {
      ctx.fillStyle = item.color;
      ctx.fillRect(item.x, item.y, item.size, item.size);
    }
  }

  draw() {
    const ctx = this.canvas.getContext("2d");
    if (!ctx) return;

    // clear canvas
    ctx.clearRect(0, 0, this.canvas.width, this.canvas.height);

    // draw items
    this.drawItems(ctx);

    // draw player
    ctx.fillStyle = "red";
    ctx.fillRect(this.x, this.y, 20, 20);
  }

  movePlayer(direction: string) {
    switch (direction) {
      case "RIGHT":
        this.x = this.x += 10;
        break;
      case "LEFT":
        this.x = this.x -= 10;
        break;

      default:
        break;
    }

    this.draw();
  }

  render() {
    return html`
      <section class="grid-container">
        <canvas id="canvas" ></canvas>
        <button @click=${() => this.movePlayer("RIGHT")}>move right</button>
        <button @click=${() => this.movePlayer("LEFT")}>move left</button>
      </section>
    `;
  }
}

declare global {
  interface HTMLElementTagNameMap {
    "canvas-grid": Grid;
  }
}
