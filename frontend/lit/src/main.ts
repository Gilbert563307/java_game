// import "./main-game.ts"
import { Router } from '@vaadin/router';
import "./shared/components/notification/notification-drop.ts"

// Select the main section where routed views will render
const bodyElement: Element | null = document.querySelector('.outlet');
const router: Router = new Router(bodyElement);

// Route guard to check if the ID parameter is valid
const isValidId = function (context: any, commands:  any): boolean | void {
  const documentId = Number(context.params.id);
  if (isNaN(documentId)) {
    return commands.redirect(`/404-NaN`);
  }
  return true;
};


router.setRoutes([
    {
        path: "/",
        component: "home-page",
        action: async () => {
            return import("./pages/home-page.ts");
        }
    },
    {
        path: "/game",
        component: "game-page",
        action: async () => {
            return import("./pages/game-page.ts");
        }
    }

]);