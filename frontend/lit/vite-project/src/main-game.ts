import { LitElement, html, css } from "lit";
import { customElement, property, state } from "lit/decorators.js";
import "./features/canvas/canvas-grid.ts";

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
    return html` <canvas-grid></canvas-grid> `;
  }
}

declare global {
  interface HTMLElementTagNameMap {
    "main-game": MainGame;
  }
}
