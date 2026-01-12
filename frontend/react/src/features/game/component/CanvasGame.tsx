import React, { useEffect, useRef, useCallback } from "react";
import grass from "../../../assets/images/grass.png";
import playerImage from "../../../assets/images/player.png";
import { Player } from "../model/player";
import type { Item } from "../model/Item";

type CanvasGameProps = {
  lootItems: Item[];
  eventName: string;
  playerX: number;
  playerY: number;
};

export const CanvasGame: React.FC<CanvasGameProps> = ({
  lootItems,
  eventName,
  playerX,
  playerY,
}) => {
  const canvasWidth = 1000;
  const canvasHeight = 600;

  const canvasRef = useRef<HTMLCanvasElement | null>(null);
  const ctxRef = useRef<CanvasRenderingContext2D | null>(null);
  const sectionRef = useRef<HTMLElement | null>(null);

  const playerRef = useRef<Player>(
    new Player(0, 0, 64, 64, playerImage, canvasWidth, canvasHeight)
  );

  
  const draw = useCallback(() => {
    const ctx = ctxRef.current;
    const player = playerRef.current;
    if (!ctx) return;

    ctx.clearRect(0, 0, canvasWidth, canvasHeight);

    ctx.drawImage(
      player.getImage(),
      Math.floor(player.getX()),
      Math.floor(player.getY()),
      player.getWidth(),
      player.getHeight()
    );

    lootItems.forEach((item) => {
      ctx.drawImage(
        item.getImage(),
        item.getX(),
        item.getY(),
        item.getWidth(),
        item.getHeight()
      );
    });
  }, [lootItems]);

  
  useEffect(() => {
    const canvas = canvasRef.current;
    if (!canvas) return;

    canvas.width = canvasWidth;
    canvas.height = canvasHeight;

    const ctx = canvas.getContext("2d");
    if (!ctx) return;

    ctxRef.current = ctx;

    const img = playerRef.current.getImage();
    img.onload = () => draw();

    sectionRef.current?.focus();
  }, [draw]);

  useEffect(() => {
    playerRef.current.updateCoordinates(playerX, playerY);
    draw();
  }, [playerX, playerY, draw]);

 
  const movePlayer = () => {
    const event = new CustomEvent(eventName, {
      detail: {
        playerCoordinates: playerRef.current.getCoordinates(),
      },
      bubbles: true,
      composed: true,
    });

    document.dispatchEvent(event);
  };

  /* ------------------------------
     Keyboard handling
  ------------------------------ */
  const handleKeyPress = (
    event: React.KeyboardEvent,
    moving: boolean
  ) => {
    const player = playerRef.current;

    switch (event.key) {
      case "ArrowUp":
        player.move("UP");
        break;
      case "ArrowDown":
        player.move("DOWN");
        break;
      case "ArrowLeft":
        player.move("LEFT");
        break;
      case "ArrowRight":
        player.move("RIGHT");
        break;
      default:
        return;
    }

    draw();

    if (!moving) {
      movePlayer();
    }
  };

  return (
    <section
      ref={sectionRef}
      tabIndex={0}
      onKeyDown={(e) => handleKeyPress(e, true)}
      onKeyUp={(e) => handleKeyPress(e, false)}
      style={{ outline: "none" }}
    >
      <canvas
        ref={canvasRef}
        style={{
          background: `url(${grass}) no-repeat`,
          backgroundSize: "cover",
          border: "2px solid black",
          imageRendering: "pixelated",
        }}
      />
    </section>
  );
};
