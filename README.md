# LittleMaidModelDevelopment

メイドさんのモデルを最新バージョンの開発環境で実行できるようにするModです。

このModを導入することによってプロジェクト内でメイドさんのモデルを実行出来るようになります。

## 自分のプロジェクトに導入する方法
<details><summary>build.gradle</summary>

```gradle
loom {
    runs {
        client {
            property("lmmd.dev.classes", "${sourceSets.main.output.getClassesDirs().asPath}")
            property("lmmd.dev.resources", "${sourceSets.main.output.getResourcesDir().absolutePath}")
            //...
        }
    }
    //...
}

repositories {
    //...
    //jitpackを追加
    maven { url 'https://jitpack.io' }
}

dependencies {
    //...
    modImplementation("com.github.Yukkuritaku:LittleMaidModelDevelopment:使用したいバージョン") {
        transitive = false
    }
}
```
</details>

## ビルド
1. [FabricのWikiページ](https://fabricmc.net/wiki/ja:tutorial:setup)にしたがってセットアップする
2. ./gradlew buildを実行する
3. 多分完了！ビルドしたJarファイルはbuild/libsに入っています。

## ライセンスに関して...

このプロジェクトのライセンスはLittleMaid Licenseです。

詳しいライセンスの説明に関してはこのプロジェクトにある[LICENSE.txt](https://github.com/Yukkuritaku/LittleMaidModelDevelopment/blob/1.20/LICENSE.txt)を見るか、sistr氏の[LICENSE.md](https://github.com/SistrScarlet/LittleMaidModelLoader-Architectury/blob/1.19.3/LICENCE.md)を参照してください。