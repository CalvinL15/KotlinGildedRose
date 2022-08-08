package guru.drako.examples.gildedrose

class Shop(val items: List<Item>) {

  private fun agedBrieItemUpdater(item: Item) {
    if (item.sellIn < 0) item.quality += 2
    else ++item.quality
  }

  private fun backstagePassesItemUpdater(item: Item) {
    when {
      // quality drops to 0 after the concert
      item.sellIn < 0 -> item.quality = 0
      // quality increases by 3 when there are 5 days or less
      item.sellIn <= 5 -> item.quality += 3
      // quality increases by 2 when there are 5 days or less
      item.sellIn <= 10 -> item.quality += 2
      // quality increases normally
      else -> ++item.quality
    }
  }

  private fun conjuredItemUpdater(item: Item) {
    // "Conjured" items degrade in quality twice as fast as normal items
    item.quality = item.quality - 2
    if (item.sellIn < 0) {
      item.quality = item.quality - 2
    }
  }

  private fun normalItemUpdater(item: Item) {
    --item.quality
    // quality degrade twice as fast if sellIn date has passed
    if (item.sellIn < 0) {
      --item.quality
    }
  }
  private fun updateItem(item: Item) {
    // check item type and update it accordingly
    when {
      item.name.contains("Aged Brie") -> agedBrieItemUpdater(item)
      item.name.contains("Backstage passes") -> backstagePassesItemUpdater(item)
      item.name.contains("Conjured") -> conjuredItemUpdater(item)
      // for basic items
      else -> normalItemUpdater(item)
    }

  }
  fun updateQuality() {
    items.forEach {
      // never modify Sulfuras
      if (!it.name.contains("Sulfuras")) {
        --it.sellIn
        updateItem(it)
        // ensure item's quality is in the range
        it.quality = it.quality.coerceIn(0, 50)
      }
    }
  }
}
