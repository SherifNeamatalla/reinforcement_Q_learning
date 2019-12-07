package snakeplayer.model;

public class SnakeStateType {

  private Long movingDirection;
  private Long foodDirectionX;
  private Long foodDirectionY;
  private Long dangerDirectionX;
  private Long dangerDirectionY;

  private Long fourDirectionsDanger;

  public Long getFourDirectionsDanger() {
    return fourDirectionsDanger;
  }

  public void setFourDirectionsDanger(Long fourDirectionsDanger) {
    this.fourDirectionsDanger = fourDirectionsDanger;
  }

  public Long getDangerDirectionY() {
    return dangerDirectionY;
  }

  public void setDangerDirectionY(Long dangerDirectionY) {
    this.dangerDirectionY = dangerDirectionY;
  }

  public Long getFoodDirectionY() {
    return foodDirectionY;
  }

  public void setFoodDirectionY(Long foodDirectionY) {
    this.foodDirectionY = foodDirectionY;
  }

  public Long getMovingDirection() {
    return movingDirection;
  }

  public void setMovingDirection(Long movingDirection) {
    this.movingDirection = movingDirection;
  }

  public Long getFoodDirectionX() {
    return foodDirectionX;
  }

  public void setFoodDirectionX(Long foodDirectionX) {
    this.foodDirectionX = foodDirectionX;
  }

  public Long getDangerDirectionX() {
    return dangerDirectionX;
  }

  public void setDangerDirectionX(Long dangerDirectionX) {
    this.dangerDirectionX = dangerDirectionX;
  }
}
