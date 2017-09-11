# ModesoActionOverlay-Android
<br/><br/>

<p align="center">
  <img src="https://media.licdn.com/mpr/mpr/shrink_200_200/AAEAAQAAAAAAAAZsAAAAJDM2NTU0MDA1LTA3YmEtNGUyMC05YmZjLTIxMDNlZWZlM2ZkMQ.png">
</p>

ModesoActionOverlay-Android is an android Animated menu for user profile screens . 
It's inspired by [Anton Aheichanka](https://dribbble.com/madebyanton)'s [design](https://dribbble.com/shots/1977070-Profile-Screen-Animation).
It enable creating animated Menu with a customized appearance

<img src="https://cdn.dribbble.com/users/62319/screenshots/1977070/shot.gif">

- [Requirements](#requirements)
- [Installation](#installation)
- [Usage](#usage)
- [Options](#options)
- [Communication](#communication)
- [Credits](#credits)
- [License](#license)

## Requirements

- minSdkVersion 16

## Installation

- **Step 1:** Add the JitPack repository to your project level build.gradle
  ```
  	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  ```
- **Step 2:** Add the dependency to your module build.gradle
  ```
  	dependencies {
	        compile 'com.github.Modeso:ModesoActionOverlay-Android:master-SNAPSHOT'
	}
  ```

## Usage

- in **XML**
```
<ch.modeso.mprofileanimatedmenu.widgets.ProfileAnimatedMenu
        android:id="@+id/profile_menu"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:animateItemOnClick="true"
        app:allowItemRotationAnim="false"
        app:itemOneIconRes="@drawable/option1_icon"
        app:itemTwoIconRes="@drawable/option2_icon"
        app:itemThreeIconRes="@drawable/option3_icon"
        app:numberOfMenuItems="3"
        app:handleIconRes="@drawable/handle_main_icon"
        app:profileImage="@drawable/my_profile_image">
    </ch.modeso.mprofileanimatedmenu.widgets.ProfileAnimatedMenu>
```
- in **Activity** or **Fragment**
```
   profileAnimatedMenu = new ProfileAnimatedMenu(context,...)
```
	- setNumberOfMenuItems(3) // 3 menu items...
	- setHandleImageRes(R.drawable.handle_image) // handle resource..
	- closeMenu() //closes the menu
	- openMenu() //opens the menu
	- OnOpenCloseListener// use to listen for open/close actions

## Options
- XML **Attributes** 
  - **animateItemOnClick**: enable/disable the menu item click animation
  - **allowItemRotationAnim**: enable/disable the menu item rotation animation
  - **numberOfMenuItems**: number of menu items .max is 5
  - **handleIconRes**: drawable for the open handle
  - **itemOneIconRes**: drawable for first menu item
  - **itemTwoIconRes**: drawable for second menu item
  - **itemThreeIconRes**: drawable for third menu item
  - **itemFourIconRes**: drawable for fourth menu item
  - **itemFiveIconRes**: drawable for fifth menu item
  - **profileImage** : drwable for the profile image  

## Communication

- If you **found a bug**, open an issue.
- If you **have a feature request**, open an issue.
- If you **want to contribute**, submit a pull request.

## Credits

ModesoActionOverlay-Android is owned and maintained by [Modeso](http://modeso.ch). You can follow them on Twitter at [@modeso_ch](https://twitter.com/modeso_ch) for project updates and releases.

## License

 ModesoActionOverlay-Android is released under the MIT license. See LICENSE for details.

