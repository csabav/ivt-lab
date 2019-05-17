package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;

  private TorpedoStore mockTorpedoStorePrimary;
  private TorpedoStore mockTorpedoStoreSecondary;

  @BeforeEach
  public void init(){
    mockTorpedoStorePrimary = mock(TorpedoStore.class);
    mockTorpedoStoreSecondary = mock(TorpedoStore.class);

    this.ship = new GT4500(mockTorpedoStorePrimary, mockTorpedoStoreSecondary);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    when(mockTorpedoStorePrimary.fire(1)).thenReturn(true);

    // Act
    ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockTorpedoStorePrimary, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
    when(mockTorpedoStorePrimary.fire(1)).thenReturn(true);
    when(mockTorpedoStoreSecondary.fire(1)).thenReturn(true);

    // Act
    ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(mockTorpedoStorePrimary, times(1)).fire(1);
    verify(mockTorpedoStoreSecondary, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_Secondary_Fail(){
    // Arrange
    when(mockTorpedoStorePrimary.fire(1)).thenReturn(true);
    when(mockTorpedoStoreSecondary.fire(1)).thenReturn(true);

    // Act
    ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockTorpedoStoreSecondary, times(0)).fire(1);
  }

  @Test
  public void fireTorpedoTwice_Single_Secondary_Success(){
    // Arrange
    when(mockTorpedoStorePrimary.fire(1)).thenReturn(true);
    when(mockTorpedoStoreSecondary.fire(1)).thenReturn(true);

    // Act
    ship.fireTorpedo(FiringMode.SINGLE);
    ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockTorpedoStorePrimary, times(1)).fire(1);
    verify(mockTorpedoStoreSecondary, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_PrimaryBlank_Success(){
    // Arrange
    when(mockTorpedoStorePrimary.isEmpty()).thenReturn(true);
    when(mockTorpedoStoreSecondary.fire(1)).thenReturn(true);

    // Act
    ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockTorpedoStoreSecondary, times(1)).fire(1);
  }

  @Test
  public void fireTorpedoTwice_Single_SecondaryBlank_Success(){
    // Arrange
    when(mockTorpedoStoreSecondary.isEmpty()).thenReturn(true);
    when(mockTorpedoStorePrimary.fire(1)).thenReturn(true);

    // Act
    ship.fireTorpedo(FiringMode.SINGLE);
    ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockTorpedoStorePrimary, times(2)).fire(1);
  }

  @Test
  public void fireTorpedo_All_SecondaryBlank_Success(){
    // Arrange
    when(mockTorpedoStorePrimary.fire(1)).thenReturn(true);
    when(mockTorpedoStoreSecondary.isEmpty()).thenReturn(true);

    // Act
    ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(mockTorpedoStorePrimary, times(1)).fire(1);
    verify(mockTorpedoStoreSecondary, times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_PrimaryBlank_SecondaryBlank_Success(){
    // Arrange
    when(mockTorpedoStorePrimary.isEmpty()).thenReturn(true);
    when(mockTorpedoStoreSecondary.isEmpty()).thenReturn(true);

    // Act
    ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockTorpedoStorePrimary, times(0)).fire(1);
    verify(mockTorpedoStoreSecondary, times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_DoubleShot_Success(){
    //Arrange_1
    when(mockTorpedoStorePrimary.fire(1)).thenReturn(true);
    when(mockTorpedoStoreSecondary.isEmpty()).thenReturn(true);

    // Act_1
    ship.fireTorpedo(FiringMode.SINGLE);

    //Arrange_2
    when(mockTorpedoStorePrimary.isEmpty()).thenReturn(true);

    // Act_2
    ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockTorpedoStorePrimary, times(1)).fire(1);
    verify(mockTorpedoStoreSecondary, times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_Default_Fail(){
    // Act
    ship.fireTorpedo(FiringMode.DEFAULT);

    // Assert
    verify(mockTorpedoStorePrimary, times(0)).fire(1);
    verify(mockTorpedoStoreSecondary, times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_All_PrimaryBlank_Success(){
    //Arrange
    when(mockTorpedoStorePrimary.isEmpty()).thenReturn(true);
    when(mockTorpedoStoreSecondary.isEmpty()).thenReturn(false);

    // Act
    ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(mockTorpedoStorePrimary, times(0)).fire(1);
    verify(mockTorpedoStoreSecondary, times(1)).fire(1);
  }
}
