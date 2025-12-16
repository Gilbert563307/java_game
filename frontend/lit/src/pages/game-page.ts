import { LitElement, html, css } from "lit";
import { customElement, property, state } from "lit/decorators.js"

@customElement("game-page")
export class GamePage extends LitElement {
    static styles = [
        css`
            :host {
                display: block;
            }
        `
    ];

    render() {
        return html`game page`;
    }
}

declare global {
    interface HTMLElementTagNameMap {
        "game-page": GamePage;
    }
}
