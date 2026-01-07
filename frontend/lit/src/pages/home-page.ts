import { LitElement, html, css } from "lit";
import { customElement } from "lit/decorators.js";
import { gameController } from "../features/game/controller/game-controller";
import { Router } from "@vaadin/router";

interface FormDataResults {
  username: string;
  worldName: string;
}

@customElement("home-page")
export class HomePage extends LitElement {
  static styles = css`
    :host {
      min-height: 100vh;
      display: flex;
      justify-content: center;
      align-items: center;
      background: #2b2b2b;
      font-family: "Minecraft", "VT323", monospace;
      color: #fff;
    }

    .panel {
      width: 520px;
      background: #3a2f25;
      border: 4px solid #1e1e1e;
      box-shadow: inset 0 0 0 2px #6b5b4a;
      padding: 20px;
      box-sizing: border-box;
    }

    h1 {
      text-align: center;
      font-size: 22px;
      margin-bottom: 16px;
      text-shadow: 2px 2px #000;
    }

    .row {
      margin-bottom: 14px;
    }

    label {
      display: block;
      font-size: 14px;
      margin-bottom: 4px;
      color: #dcdcdc;
    }

    input {
      width: 100%;
      background: #000;
      color: #fff;
      border: 2px solid #5f5f5f;
      padding: 6px 8px;
      font-size: 16px;
      box-sizing: border-box;
      outline: none;
    }

    input:focus {
      border-color: #ffffff;
    }

    .hint {
      font-size: 12px;
      color: #a0a0a0;
      margin-top: 4px;
    }

    .buttons {
      display: flex;
      gap: 12px;
      margin-top: 24px;
    }

    button {
      flex: 1;
      padding: 10px 0;
      background: #7d7d7d;
      border: 2px solid #000;
      color: #fff;
      font-size: 16px;
      cursor: pointer;
      text-shadow: 1px 1px #000;
    }

    button:hover {
      background: #8b8b8b;
    }

    button.primary {
      background: #6f6f6f;
    }

    button.primary:hover {
      background: #7f7f7f;
    }
  `;

  getFormData(event: Event): FormDataResults {
    event.preventDefault();
    const form: HTMLFormElement = event.target;
    const data = new FormData(form);
    return Object.fromEntries(data);
  }

  async startGame(event: Event) {
    const data = this.getFormData(event);
    const response = await gameController.createGame(data);
    if(Object.keys(response).length > 0){
      Router.go("/game")
    }
  }

  render() {
    return html`
      <form class="panel" @submit=${this.startGame}>
        <h1>Create New World</h1>

        <div class="row">
          <label for="worldName">World Name</label>
          <input
            id="worldName"
            name="worldName"
            type="text"
            placeholder="New World"
          />
        </div>

        <div class="row">
          <label for="username">Username</label>
          <input
            id="username"
            name="username"
            type="text"
            placeholder="Steve"
          />
          <div class="hint">Use for player name for identity</div>
        </div>

        <div class="buttons">
          <button class="primary" type="submit">
            Create New World
          </button>
        </div>
      </form>
    `;
  }
}

declare global {
  interface HTMLElementTagNameMap {
    "home-page": HomePage;
  }
}
