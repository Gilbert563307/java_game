type Direction = "UP" | "DOWN" | "RIGHT" | "LEFT";

export class Player {
  x: number;
  y: number;
  width: number;
  height: number;
  imageString: string;
  #image: HTMLImageElement;
  #maxWidth: number;
  #maxHeight: number;

  // #movingUp: boolean = false

  constructor(
    x: number,
    y: number,
    width: number,
    height: number,
    imageString: string,
    maxWidth: number,
    maxHeight: number
  ) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.imageString = imageString;
    this.#maxWidth = maxWidth;
    this.#maxHeight = maxHeight;
    this.#initImage();
  }

  getX(): number {
    return this.x;
  }

  getY(): number {
    return this.y;
  }

  getWidth(): number {
    return this.width;
  }

  getHeight(): number {
    return this.height;
  }

  getImage(): HTMLImageElement {
    return this.#image;
  }

  getCoordinates(): { x: number; y: number } {
    return { x: this.x, y: this.y };
  }

  move(direction: Direction) {
    if (direction === "UP") {
      const newY = this.y + -5;
      
      //Canvas top border
      if (newY >= 0) {
        this.y = newY;
      }
    }

    if (direction === "DOWN") {
      const newY: number = this.y + 5;

      //is player at bottom
      if (newY < this.#maxHeight - this.height) {
        this.y = newY;
      }
    }

    if (direction === "RIGHT") {
      const newX: number = this.x + 5;
      if (newX < this.#maxWidth - this.width) {
        this.x += 5;
      }
    }

    if (direction === "LEFT") {
      const newX: number = this.x - 5;
      if (newX > 0) {
        this.x -= 5;
      }
    }
  }

  #initImage() {
    const image = new Image();
    image.src = this.imageString;
    this.#image = image;
  }
}
