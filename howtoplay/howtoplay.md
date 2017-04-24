# What is this?

This is meisterfuu's and elektropapst's entry for the 15th [LudumDare](https://ldjam.com/) anniversary (38)

In total we participated in three GameJams:
* LibGDX Jam
    - meisterfuu, elektropapst
* LudumDare 35
    - meisterfuu, arse, elektropapst
* LudumDare 38
    - meisterfuu, elektropapst

# How to Play

After we started the game, the screen looks like this:

![start][start]

We play on an 8x8 Hex grid, with our spawn located in the top left corner (AI-opponent bottom right).
The current amount of warriors for a single Hex tile is shown by the corresponding number (e.g. `10`).
For each game tick our castle "produces" new warriors (max. `10` at the beginning) which
we can send across the field.

Let's send some warriors from our spawn (namely the Hex Castle) to accessible tiles.
Our empire could look like this:

![steps][steps]

As you can see, we captured two windmills, each increasing our castle's `maxpopulation` by 2 (for each windmill).
So our maximum warriors in our castle is `10+2+2=14`. We can also send warriors from other
Hex tiles by selecting it with the right mouse button. Remember that each captured Hex tile
must have at least 5 warriors until you can select and send from that tile.

Let us learn how to move our warriors in the next section:

## Controls

We control the game entirely using our mouse:

 &nbsp;                             | Key                   | FOO                   |
------------------------------------|-----------------------|-----------------------|
![Left mouse click][leftclick]      | Left mouse click      | Send a single warrior of the selected Hex   |
![Left mouse click][leftclick]      | Left mouse long press | Send half of the selected Hex's warriors         |
![Right mouse click][rightclick]    | Right mouse click     | Select another Hex (The arrow indicates the currently selected Hex)          |
![Left mouse click][leftclick]![Right mouse click][rightclick]    | Left or right mouse drag     | Drag the map|
![Scroll][scroll]                   | Scroll | Zoom map        |


## Buildings

As already mentioned above, capturing tiles with buildings can boost you:

| Image                         | Building          | Found on              | Explanation                                   |
|:-----------------------------:|-------------------|-----------------------|-----------------------------------------------|
| ![Castle][castle]             | Castle            | ![Any][any]           |Your Hex Castle spawn                          |
| ![Sand house][beige]          | Sand house        | ![Sand][sand]         |Increases your Hex Castle's `spawnrate`        |
| ![Tent][tent]                 | Desert Tent       | ![Sand][sand]         |Increases your Hex Castle's `spawnrate`        |
| ![Hangar][hangar]             | Hangar            | ![Dirt][dirt]         |Increases all units `movementspeed`            |
| ![Military tent][militaryTent]| Military Tent     | ![Dirt][dirt]         |Increases all units `movementspeed`            |
| ![Windmill][windmill]         | Windmill          | ![Grass][grass]       |Increases your Hex Castle's `maxpopulation`    |
| ![Tavern][tavern]             | Tavern            | ![Grass][grass]       |Increases your Hex Castle's `maxpopulation`    |
| ![Skyscraper][skyscraper]     | Skyscraper Glass  | ![Stone][stone]       |Increases your Hex Castle's `tickrate`         |
| ![Skyscraper][skyscraperWide] | Skyscraper Wide   | ![Stone][stone]       |Increases your Hex Castle's `tickrate`         |

...where...

* `spawnrate` = Numbers of warriors spawned in each tick
* `movementspeed` = Speed of warriors
* `maxpopulation` = Maximum amount of warriors in the Hex Castle spawn
* `tickrate` = How fast the warrior spawn ticks

Fortunately we can see a legend located at the window's bottom border, so we don't need to remember all buildings and their effects:

![legend][Legend]

## Goal

It is as simple as it sounds:

Eliminate your AI-opponent by capturing **all** AI's Hex tiles.
This includes all AI captured tiles.

### Thanks for reading and have fun with this game :)
&mdash; meisterfuu & elektropapst

## Credits

We are incredibly thankful for the assets from:

* Graphics: http://www.kenney.nl/
* Music: http://www.bensound.com/

[beige]: beigeBuilding.png "Beige"
[castle]: castle_open.png "Castle"
[hangar]: hangar.png "Beige"
[skyscraper]: skyscraper_glass.png "Beige"
[windmill]: windmill_complete.png "Beige"

[tent]: indianTent_front.png "Tent"
[militaryTent]: militaryTent.png "Mililitary tent"
[skyscraperWide]: skyscraper_wide.png "Skyscraper Wide"
[tavern]: tavern.png "Tavern"

[leftclick]: leftsmall.png "Beige"
[rightclick]: right.png "Beige"
[scroll]: scroll.png "Beige"

[grass]: grass.png "grass"
[sand]: sand.png "sand"
[stone]: stone.png "stone"
[dirt]: dirt.png "dirt"
[any]: all.png "dirt"

[start]: start.png "Start"
[steps]: start2.png "Steps"
[legend]: legend.png "Legend"