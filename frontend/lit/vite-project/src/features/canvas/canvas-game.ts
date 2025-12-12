import { LitElement, html, css } from "lit";
import { customElement, property, state } from "lit/decorators.js"

@customElement("canvas-game")
export class CanvasGame extends LitElement {
    static styles = [
        css`
            :host {
                display: block;
            }
        `
    ];

    render() {
        return html``;
    }
}

declare global {
    interface HTMLElementTagNameMap {
        "canvas-game": CanvasGame;
    }
}
