import { LitElement, html, css } from "lit";
import { customElement, property } from "lit/decorators.js";
import { unsafeHTML } from "lit/directives/unsafe-html.js";
import { ALERT_TYPES } from "../../../config/constants";
import { notificationSubjectService } from "../../observers/notification-subject-service";

export interface Notification {
  message: string;
  type: number;
}

interface Observer {
  next: Function;
}

const FIRST_ARR_ELEMENT = 0;
const ARR_LENGTH_ONE = 1;
@customElement("notification-drop")
export class NotificationDrop extends LitElement {
  data: Notification = { message: "", type: 0 };
  @property({ type: Boolean })
  visible: boolean = false;
  //need to bind to this 
  observer: Observer = {
    next: (observerData: any) => this.next(observerData),
  };

  static styles = [
    css`
      .notification {
        position: absolute;
        overflow-y: scroll;
        top: 0px;
        right: 0;
        margin: 2em;
        background-color: #fff;
        border-left: 4px solid #cac9cc;
        width: 450px;
        min-height: 150px;
        box-shadow: rgba(0, 0, 0, 0.24) 0px 3px 8px;
      }

      .notification-message {
        margin: 1em;
      }

      .alert-info {
        border-left: 3px solid #cff4fc;
        box-shadow: rgba(207, 244, 252, 0.24) 0px 3px 8px;
      }

      .alert-danger {
        border-left: 3px solid #f8d7da;
        box-shadow: rgba(248, 215, 218, 0.24) 0px 3px 8px;
      }

      .alert-success {
        border-left: 3px solid #d1e7dd;
        box-shadow: rgba(209, 231, 221, 0.24) 0px 3px 8px;
      }

      .alert-primary {
        border-left: 3px solid #cfe2ff;
        box-shadow: rgba(207, 226, 255, 0.24) 0px 3px 8px;
      }

      .close-btn {
        position: absolute;
        top: 6px;
        right: 10px;
        font-size: 1.2rem;
        background: none;
        border: none;
        color: #888;
        cursor: pointer;
        transition: color 0.2s;
      }

      .close-btn:hover {
        color: #000;
      }

      .hide {
        display: none;
      }

      .show {
        display: block;
      }
    `,
  ];

  static getAlertTypeClass(type: Number): string {
    const castedType = Number(type);
    const ALERT_TYPE_MAP = {
      [ALERT_TYPES.INFO]: "alert-info",
      [ALERT_TYPES.DANGER]: "alert-danger",
      [ALERT_TYPES.SUCCESS]: "alert-success",
      [ALERT_TYPES.PRIMARY]: "alert-primary",
    };

    return ALERT_TYPE_MAP[castedType] || ALERT_TYPE_MAP[ALERT_TYPES.INFO];
  }

  hideNotification(): void {
    this.visible = false;
  }

  next(observerData: Array<{ message: string; type: number }>) {
    if (observerData.length === ARR_LENGTH_ONE) {
      const notification: Notification = observerData[FIRST_ARR_ELEMENT];
      this.data.message = notification.message;
      this.data.type = notification.type;
      this.visible = true;
    }
  }

  connectedCallback(): void {
    super.connectedCallback();
    notificationSubjectService.subscribe(this.observer);
  }

  disconnectedCallback(): void {
    super.connectedCallback();
    notificationSubjectService.unsubscribe(this.observer);
  }

  render() {
    return html`
      <article
        class="notification ${NotificationDrop.getAlertTypeClass(
          this.data.type
        )} ${this.visible ? "show" : "hide"}"
      >
        <button class="close-btn" @click=${this.hideNotification} title="Close">
          ×
        </button>
        <p class="notification-message">${unsafeHTML(this.data.message)}</p>
      </article>
    `;
  }
}

declare global {
  interface HTMLElementTagNameMap {
    "notification-drop": NotificationDrop;
  }
}
