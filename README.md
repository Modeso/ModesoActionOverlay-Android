# ModesoActionOverlay-Android
<br/><br/>

<p align="center">
  <img src="https://media.licdn.com/mpr/mpr/shrink_200_200/AAEAAQAAAAAAAAZsAAAAJDM2NTU0MDA1LTA3YmEtNGUyMC05YmZjLTIxMDNlZWZlM2ZkMQ.png">
</p>

ModesoActionOverlay-Android is an android Animated menu for user profile screens . 
It's inspired by [Anton Aheichanka](https://dribbble.com/madebyanton)'s [design](https://dribbble.com/shots/1977070-Profile-Screen-Animation).
It enable creating questionnaire with a lot of features and ease of use

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
	        compile 'com.github.Modeso:ModesoActionOverlay-Android:development-SNAPSHOT'
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
        app:handleIconRes="@drawable/icons_ellipsis"
        app:profileImage="@drawable/friendly_face">
    </ch.modeso.mprofileanimatedmenu.widgets.ProfileAnimatedMenu>
```
- in **Activity** or **Fragment**
```
  mcompound_questionnaire.updateList(title.toMutableList())
  mcompound_questionnaire.cardInteractionCallBacks = this
```
	- use updateList(itemsList: MutableList<BaseModel>) to create/update question list
	- use cardInteractionCallBacks to set callback for user action on each question

## Options
- XML **Attributes**
  - **mcqIndicatorBackgroundColor**: indicator inner background color
  - **mcqIndicatorUpperColor**: indicator upper background color
  - **mcqIndicatorLowerColor**: indicator lower background color
  - **mcqIndicatorDrawableIcon**: indicator drawable icon
  - **mcqIndicatorSizeFraction**: indicator height relative to inner background color (value 1 :indcator height = inner background height)
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

ModesoQuestionnaire-Android is owned and maintained by [Modeso](http://modeso.ch). You can follow them on Twitter at [@modeso_ch](https://twitter.com/modeso_ch) for project updates and releases.

## License

ModesoQuestionnaire-Android is released under the MIT license. See LICENSE for details.

