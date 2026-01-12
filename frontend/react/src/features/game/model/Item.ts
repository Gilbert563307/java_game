export class Item {
  x: number;
  y: number;
  imageString: string;

  #width: number = 64;
  #height: number = 64;
  #image: HTMLImageElement;

  constructor(x: number, y: number, imageString: string) {
    this.x = x;
    this.y = y;
    this.imageString = imageString;
    this.#initImage();
  }

  getX(): number {
    return this.x;
  }

  getY(): number {
    return this.y;
  }

  getImage(): HTMLImageElement {
    return this.#image;
  }

  getWidth(): number {
    return this.#width;
  }

  getHeight(): number {
    return this.#height;
  }

  #initImage() {
    const image = new Image();
    image.src = this.imageString;
    this.#image = image;
  }
}
