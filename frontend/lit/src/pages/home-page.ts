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
      display: flex;
      justify-content: center;
      box-sizing: border-box;
    }

    .container {
      padding: 24px;
      display: flex;
      flex-direction: column;
      align-items: center;
      gap: 1em;
    }

    h1 {
      margin-top: 40px;
      margin-bottom: 48px;
      font-size: 28px;
      font-weight: 600;
      text-align: center;
    }

    label {
      display: block;
      margin-bottom: 8px;
      font-size: 14px;
      color: #333;
    }

    input {
      width: 100%;
      padding: 10px 12px;
      font-size: 16px;
      border: 1px solid #dcdcdc;
      border-radius: 4px;
      outline: none;
    }

    input:focus {
      border-color: #1e90ff;
    }

    .spacer {
      flex: 1;
    }

    button {
      margin-bottom: 24px;
      padding: 10px 32px;
      font-size: 16px;
      color: #fff;
      background-color: #0b3a66;
      border: none;
      border-radius: 6px;
      cursor: pointer;
    }
  `;

  getFormData(event: Event): FormDataResults {
    event.preventDefault();
    const form = event.target;
    if (form == null) {
      return { worldName: "", username: "" };
    }
    const data = new FormData(form);
    return Object.fromEntries(data);
  }

  async startGame(event: Event) {
    const data: FormDataResults = this.getFormData(event);
    const { game } = await gameController.createGame(data);
    console.log(game)
    // if (Object.keys(game).length > 0) {
    //   Router.go("game");
    // }
  }

  render() {
    return html`
      <form class="container" @submit=${this.startGame}>
        <div>
          <label for="username">Username</label>
          <input id="username" type="text" name="username" />
        </div>
        <div>
          <label for="worldName">world name</label>
          <input id="worldName" type="text" name="worldName_" />
        </div>
        <button name="submit">Start</button>
      </form>
    `;
  }
}

declare global {
  interface HTMLElementTagNameMap {
    "home-page": HomePage;
  }
}
