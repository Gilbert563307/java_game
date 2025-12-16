import { LitElement, html, css } from "lit";
import { customElement, property, state } from "lit/decorators.js";
// import "./features/canvas/canvas-grid.ts";
// import "./features/canvas/moo-canvas.ts";
import "./features/canvas/canvas-game.ts";


@customElement("main-game")
export class MainGame extends LitElement {
  constructor() {
    super();
  }

  static styles = [
    css`
      :host {
        display: flex;
        justify-content: center;
      
      }
    `,
  ];

  render() {
    return html` <canvas-game></canvas-game> `;
  }
}

declare global {
  interface HTMLElementTagNameMap {
    "main-game": MainGame;
  }
}
