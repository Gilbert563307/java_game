interface CoordinatesDto {
  id: number;
  x: number;
  y: number;
}

interface ItemDto {
  id: number;
  name: string;
  coordinates: CoordinatesDto;
}

interface InventoryDto {
  id: number,
  inventoryItems: Array<ItemDto>
}

export interface PlayerDto {
  id: number;
  username: string,
  health: number,
  experience: string,
  inventory: InventoryDto,
  coordinates: CoordinatesDto,
}

interface WorldMapDto {
  lootItems: Array<ItemDto>;
  playerList: Array<PlayerDto>;
}

export interface GameDto {
  id: number;
  worldName: number;
  world: WorldMapDto;
}
