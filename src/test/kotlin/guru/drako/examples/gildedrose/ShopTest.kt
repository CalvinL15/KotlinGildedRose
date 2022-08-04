package guru.drako.examples.gildedrose

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import kotlin.random.Random

class ShopTest {

  @Test
  fun testGeneralUpdateQuality(){
    val shop = Shop(items = listOf(
      Item(name = "+5 Dexterity Vest", sellIn = 10, quality = 20),
      Item(name = "Aged Brie", sellIn = 2, quality = 0),
      Item(name = "Elixir of the Mongoose", sellIn = 5, quality = 7),
      Item(name = "Sulfuras, Hand of Ragnaros", sellIn = 0, quality = 80),
      Item(name = "Backstage passes to a TAFKAL80ETC concert", sellIn = 15, quality = 20),
      Item(name = "Conjured Mana Cake", sellIn = 3, quality = 6)
    ))
    shop.updateQuality()
    assertEquals(19, shop.items[0].quality)
    assertEquals(1, shop.items[1].quality)
    assertEquals(6, shop.items[2].quality)
    assertEquals(80, shop.items[3].quality)
    assertEquals(21, shop.items[4].quality)
    assertEquals(4, shop.items[5].quality)
  }

  @Test
  fun testUpdateQualityAfterPassedSellIn(){
    val shop = Shop(items = listOf(
      Item(name = "+5 Dexterity Vest", sellIn = 0, quality = 20),
      Item(name = "Aged Brie", sellIn = 0, quality = 0),
      Item(name = "Elixir of the Mongoose", sellIn = 0, quality = 7),
      Item(name = "Sulfuras, Hand of Ragnaros", sellIn = 0, quality = 80),
      Item(name = "Backstage passes to a TAFKAL80ETC concert", sellIn = 0, quality = 20),
      Item(name = "Conjured Mana Cake", sellIn = 0, quality = 6)
    ))
    shop.updateQuality()
    assertEquals(18, shop.items[0].quality)
    assertEquals(2, shop.items[1].quality)
    assertEquals(5, shop.items[2].quality)
    assertEquals(80, shop.items[3].quality)
    assertEquals(0, shop.items[4].quality)
    assertEquals(2, shop.items[5].quality)
  }

  @Test
  fun testNeverChangingSulfuras(){
    val shop = Shop(items = listOf(
      Item(name = "Sulfuras, Hand of Ragnaros", sellIn = 0, quality = 80)
    ))
    var randomNum = Random.nextInt(1, 3)
    while (randomNum > 0) {
      shop.updateQuality()
      randomNum--
    }
    assertEquals(0, shop.items[0].sellIn)
    assertEquals(80, shop.items[0].quality)
  }

  @Test
  fun testQualityStaysInRange(){
    val shop = Shop(items = listOf(
      Item(name = "+5 Dexterity Vest", sellIn = 2, quality = 0),
      Item(name = "Aged Brie", sellIn = 3, quality = 50),
      Item(name = "Elixir of the Mongoose", sellIn = 0, quality = 1),
      Item(name = "Backstage passes to a TAFKAL80ETC concert", sellIn = 1, quality = 48),
      Item(name = "Conjured Mana Cake", sellIn = 0, quality = 1)
    ))
    shop.updateQuality()
    assertEquals(0, shop.items[0].quality)
    assertEquals(50, shop.items[1].quality)
    assertEquals(0, shop.items[2].quality)
    assertEquals(50, shop.items[3].quality)
    assertEquals(0, shop.items[4].quality)
  }

  @Test
  fun testBackstagePassItems(){
    val shop = Shop(items = listOf(
      Item(name = "Backstage passes to a TAFKAL80ETC concert", sellIn = 0, quality = 20),
      Item(name = "Backstage passes to a TAFKAL81ETC concert", sellIn = 4, quality = 20),
      Item(name = "Backstage passes to a TAFKAL82ETC concert", sellIn = 8, quality = 20),
      Item(name = "Backstage passes to a TAFKAL83ETC concert", sellIn = 16, quality = 20)
    ))
    shop.updateQuality()
    // quality drops to 0 after the concert
    assertEquals(0, shop.items[0].quality)
    // quality increases by 3 when there are 5 days or less
    assertEquals(23, shop.items[1].quality)
    // quality increases by 2 when there are 10 days or less
    assertEquals(22, shop.items[2].quality)
    assertEquals(21, shop.items[3].quality)
  }
}