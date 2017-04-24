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

The current amount of warriors in one Hex tile is shown by the corresponding number.
In each game tick your castle "produces" new warriors (max. 10 at the beginning) which
we can send around the field.

Let's send some warriors from our spawn (Hex castle) to accessible tiles.
Our empire could look like this:

![steps][steps]

As you can see, we captured two windmills, each increasing our castle's `maxpopulation` by 2.
So our maximum warriors inside our castle is `10+2+2=14` (Yes, there are more buildings). We can also send warriors from other
Hex tiles by selecting with the right mouse button. Remember that each captured Hex tile
must have at least 5 warriors until you can select and send from that tile

Let us learn how to move our warriors in the next section:

## Controls

We control the game entirely using our mouse:

 &nbsp;                             | Key                   | FOO                   |
------------------------------------|-----------------------|-----------------------|
![Left mouse click][leftclick]      | Left mouse click      | Send a single warrior of the selected Hex   |
![Left mouse click][leftclick]      | Left mouse long press | Send half of the selected Hex's warriors         |
![Right mouse click][rightclick]    | Right mouse click     | Select another Hex (The arrow indicates the currently selected Hex)          |
![Scroll][scroll]                   | Scroll | Zoom map        |


## Buildings

As already mentioned above, capturing tiles with buildings can boost you:

| Image                       | Building   | Found on | Explanation                  |
|:-------------------------:|------------|----------|----------------------|
| ![Castle][castle]       | Castle     | ![Any][any]      |Your Hex castle spawn |
| ![Sand house][beige]        | Sand house | ![Sand][sand]     |Increases your Hex castle's `spawnrate` |
| ![Hangar][hangar]       | Hangar     | ![Dirt][dirt]     |Increases all units `movementspeed` |
| ![Windmill][windmill]     | Windmill   | ![Grass][grass]    |Increases your Hex castle's `maxpopulation` |
| ![Skyscraper][skyscraper]   | Skyscraper | ![Stone][stone]    |Increases your Hex castle's `tickrate`  |

## Goal

It is as simple as it sounds:

Eliminate your AI-opponent by capturing all AI's Hex tiles (including the spawn).

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
[leftclick]: leftsmall.png "Beige"
[rightclick]: right.png "Beige"
[scroll]: scroll.png "Beige"

[grass]: grass.png "grass"
[sand]: sand.png "sand"
[stone]: stone.png "stone"
[dirt]: dirt.png "dirt"
[any]: all.png "dirt"

[start]: startgrid.png "dirt"
[steps]: steps.png "dirt"