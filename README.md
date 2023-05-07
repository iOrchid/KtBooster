# 🚀KtBooster

🚀🚀Kotlin语言构建的Android开发便捷库或架构(An android development efficiency multiplier using kotlin as a dependency library or architecture)
[![jetpack](https://img.shields.io/badge/志威-KtBooster-brightgreen.svg)](https://github.com/iOrchid/KtBooster)[![apache2.0](https://img.shields.io/badge/license-apache2.0-brightgreen.svg)](./LICENSE) [![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=21) ![](https://img.shields.io/github/workflow/status/iOrchid/KtBooster/CI) ![GitHub repo size](https://img.shields.io/github/repo-size/iOrchid/KtBooster.svg?style=flat-square) ![GitHub code size in bytes](https://img.shields.io/github/languages/code-size/iOrchid/KtBooster) ![GitHub last commit](https://img.shields.io/github/last-commit/iOrchid/KtBooster.svg?style=flat-square) ![GitHub commit activity](https://img.shields.io/github/commit-activity/m/iOrchid/KtBooster.svg?style=flat-square)![GitHub All Releases](https://img.shields.io/github/downloads/iOrchid/KtBooster/total) [![Hits](https://hits.seeyoufarm.com/api/count/incr/badge.svg?url=https%3A%2F%2Fgithub.com%2FiOrchid%2FKtBooster&count_bg=%2379C83D&title_bg=%23555555&icon=&icon_color=%23E7E7E7&title=Hits&edge_flat=false)](https://github.com/iOrchid/KtBooster)

#### 一、项目简介[![jitpack](https://jitpack.io/v/iOrchid/KtBooster.svg)](https://jitpack.io/#iOrchid/KtBooster)![GitHub release (latest SemVer including pre-releases)](https://img.shields.io/github/v/release/iOrchid/KtBooster?include_prereleases)![GitHub Release Date](https://img.shields.io/github/release-date-pre/iOrchid/KtBooster.svg?color=orange&style=flat-square)

> **KtBooster**是一个`Android`开发快速启动的项目`依赖库`或者`框架`，使用`Kotlin`语言结合`Jetpack`组件，提供项目开发常用的各类工具、组件和便捷库。

当前版本上处于建库开发初期，欢迎感兴趣的童鞋一同协作。整个架构和API封装尚在进行中，并测试发布`Jitpack.io`仓库，用于依赖库内测使用

#### 二、框架结构与项目规划

- 项目结构初步以个人项目实践的积累实用性为基础，划分如下模块
  - [ ] 通用工具类库的封装
  - [ ] `ui`相关控件库的封装
  - [ ] 基础网络库的封装支持`okhttp`、`retrofit`
  - [ ] `WebView`库的封装，添加`JsBridge`，支持`x5`内核
  - [ ] 其他后续需要的模块
- 项目规划一步步先由个人常用工具库的整理，逐步扩展为快速开发`App`的通用库/框架，以此来完善设计模式和架构能力

#### 三、Why？How？

*项目自身更多的学习与沉淀，为何要做一个已经存在众多同类项目的仓库？这个问题两个回答点：*

1. 自身学习的沉淀与提升
2. 力求做出更顺手顺心和青出于蓝的产品

如何使用？

```groovy
//project build.gradle
repositories{
  maven{url "https://jitpack.io}
}
//app build.gradle
  dependencies{
    implementation("com.github.iOrchid:KtBooster:$version")//$version
  }
```

添加`jitpack.io`到仓库，然后使用`implementation`即可，`version`参见`ReadMe`上面的`jitpack`标签

**TODO：才刚开始，所以还没有演示文档，一切以code为最标准**

