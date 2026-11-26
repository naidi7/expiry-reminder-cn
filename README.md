# 过期提醒（安卓中文分类版）

一个纯本地、无广告、无联网的安卓应用，用来提醒你自己购买的日常用品什么时候过期。

本项目基于开源项目 [lorenzovngl/FoodExpirationDates](https://github.com/lorenzovngl/FoodExpirationDates)（MIT 协议）改造，在其成熟的数据存储、通知和 Material You 界面基础上，按中文用户的使用习惯做了定制。

## 已实现功能

- **三类物品**：食品 / 生鲜、药品 / 保健品、化妆品 / 护肤品，添加时可选分类，列表顶部可按分类筛选，卡片上用不同文字标签区分。
- **灵活提醒**：每个物品可单独设置「提前几天提醒」（0～30 天），进入提醒窗口后每天提醒一次，到期当天必定提醒。已过期物品不再每天重复通知，但仍会在列表中醒目标出。
- **手动录入**：填写名称 + 过期日期即可，同时支持数量、开封日期、开封后可使用天数（用于化妆品/护肤品）。
- **中文界面 + Material You**：跟随系统的动态配色，支持浅色/深色主题，临期和过期用颜色区分。
- **纯本地存储**：Room 数据库保存在手机本地，不需要注册登录，不联网，无 Firebase 依赖。
- **数据备份**：设置里可导出 CSV / 从 CSV 导入（分类和提醒天数使用默认值）。
- **引导页**：首次打开会引导开启通知权限并选择每日提醒时间。

## 三种编译方式对比

| 方式 | 需要本地装环境？ | 大致耗时 | 适合谁 |
| --- | --- | --- | --- |
| 方式一：GitHub Actions 在线构建 | 不需要，只需浏览器 | 3～10 分钟 | 不想折腾环境、只想拿到 APK 的人 |
| 方式二：Android Studio | 要（Android Studio 自带 JDK，无需单独装 Java；需下载 SDK） | 首次 30 分钟～2 小时 | 想继续开发、调试的人 |
| 方式三：命令行 | 要（JDK 21 + Android SDK + Gradle） | 首次同上 | 熟悉命令行的人 |

> 就想装上用的话，直接走方式一。

## 编译环境要求（方式二、三才需要）

| 项目 | 要求 |
| --- | --- |
| Android Studio | 支持 Android Gradle Plugin 9.5.2 的版本（请使用较新的 Android Studio） |
| JDK | 21（Android Studio 通常自带，无需单独安装） |
| Android SDK | compileSdk 37，minSdk 26（Android 8.0 及以上） |
| 网络 | 首次同步 Gradle 依赖时需要联网 |

> 当前电脑没有 Java / Android SDK，因此源码尚未在此环境编译过。首次打开工程时请耐心等待 Gradle 下载依赖。

## 编译成 APK 的步骤

### 方式一：GitHub Actions 在线构建（最省事，无需安装任何环境）

工程里已预置 `.github/workflows/build-apk.yml`，构建在云端完成，你只需要一个浏览器：

1. 注册/登录 GitHub，新建一个空仓库（建议设为 Public，私有仓库也行但有免费额度限制）。
2. 把本工程目录（`ExpiryReminder-src`）里的**全部文件**（包括隐藏的 `.github` 文件夹）上传到仓库。
3. 打开仓库页面 → **Actions** 标签页 → 左侧选择 **构建过期提醒 APK** → 点击 **Run workflow**（或首次 `push` 代码时会自动触发）。
4. 等待几分钟（通常 3～10 分钟），构建完成后点进这次运行，在页面底部 **Artifacts** 处下载 `过期提醒-APK`，解压得到 APK。
5. 把 APK 传到手机安装（需要允许「安装未知来源应用」）。

> 注意：上传时请确保 `.github` 文件夹也被传上去（它在文件列表里默认隐藏，GitHub 网页上传时需要显示隐藏文件，或用 `git` 命令上传）。

### 方式二：Android Studio（需安装环境，约 1+ GB 下载）

1. 打开 Android Studio，选择 **Open**，选中本工程目录（`ExpiryReminder-src`，即包含 `settings.gradle` 和 `app` 文件夹的那一层）。
2. 等待右下角 Gradle 同步完成。若提示缺少 SDK，按提示安装 API 37（或勾选自动安装）。
3. 菜单栏选择 **Build → Build Bundle(s) / APK(s) → Build APK(s)**。
4. 编译完成后，在右下角提示中点击 **locate**，找到：

   ```
   app/build/outputs/apk/foss/debug/app-foss-debug.apk
   ```

5. 把 APK 传到手机上，在手机上点击安装（需要允许「安装未知来源应用」）。

### 方式三：命令行（需本地环境）

在工程根目录执行：

```bash
./gradlew assembleFossDebug
```

产物路径：`app/build/outputs/apk/foss/debug/app-foss-debug.apk`

如需给手机直接安装（已连接 USB 调试）：

```bash
adb install app/build/outputs/apk/foss/debug/app-foss-debug.apk
```

### 关于 `foss` 版本

`gradle.properties` 中已设置 `buildFoss=true`，因此工程默认构建 `foss` 变体：

- 不含 Firebase 统计、崩溃上报、条形码扫码和联网商品查询功能；
- **不需要** `google-services.json` 文件；
-  reminder 通知完全由本地 WorkManager 调度。

其他功能（清单、分类、提醒、导入导出）在 `foss` 版本中均可用。

## 首次使用

1. 打开应用，在引导页允许「通知」权限（Android 13+ 必须）。
2. 选择每天检查提醒的时间，例如早上 8:00。
3. 点击右下角「+」添加物品：填名称、选分类、选过期日期、设置提前提醒天数。
4. 打开应用即可看到按到期时间排序的清单，顶部可按分类筛选。

## 注意事项

- **后台提醒**：国产 ROM（小米、华为、OPPO、vivo 等）可能会限制后台运行。为保证提醒准时，请在系统设置中为本应用开启「自启动」「后台运行」和「电池不优化」权限。
- **数据保存**：数据只在手机本地，卸载应用会丢失。换手机前请在「设置」中导出 CSV 备份。
- **通知时机**：提醒在每天设定的时间检查一次；如果当时手机关机或应用被彻底冻结，可能收不到当天的通知。
- **导入导出**：CSV 格式沿用原项目，不包含新增的分类和提醒天数字段，导入后这两项会恢复默认值（食品 / 提前 3 天）。

## 本次改造涉及的主要文件

| 文件 | 改动 |
| --- | --- |
| `model/entity/ExpirationDate.kt` | 新增 `category`、`notifyDaysBefore` 字段和分类常量 |
| `model/AppDatabase.kt` / `di/AppModule.kt` | 数据库升级到 v4，添加 3→4 迁移 |
| `feature/foodeditor/.../FoodEditorScreen.kt` | 分类选择、提前提醒天数输入 |
| `feature/foodlist/.../FoodCard.kt` | 卡片显示分类标签 |
| `feature/foodlist/.../FoodItemsList.kt`、`FoodListScreen.kt` | 列表顶部分类筛选 |
| `feature/foodlist/.../FoodCardUiModel.kt`、`FoodCardUiModelMapper.kt` | UI 模型传递分类 |
| `model/worker/CheckExpirationsWorker.kt` | 按自定义提前天数提醒，跳过已过期物品 |
| `res/values/strings.xml` | 全面中文化，新增分类和提醒文案 |
| `gradle.properties` | 启用 `buildFoss=true`，去掉 Firebase 依赖需求 |

## 协议

原项目代码遵循 MIT 协议，详见 `LICENSE`。改造部分同样遵循 MIT 协议。
