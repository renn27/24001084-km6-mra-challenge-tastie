package com.refood.tastie.data.datasource.menu

import com.refood.tastie.data.model.Menu

class DummyMenuDataSource : MenuDataSource {
    override fun getMenus(): List<Menu> {
        return mutableListOf(
            Menu(
                name = "Ayam Bakar",
                price = 40000.0,
                imagePictUrl = "https://cdn0-production-images-kly.akamaized.net/M63Fm-0RE7Kup8ELu__IRAFJr-U=/0x0:3000x1691/1200x675/filters:quality(75):strip_icc():format(jpeg)/kly-media-production/medias/3910470/original/073110400_1642740733-shutterstock_1854744190.jpg",
                description = "Potongan ayam yang dimasak dengan sempurna, berpadu dengan rempah-rempah khas yang meresap hingga ke dalam dagingnya, menyajikan kelezatan yang menggoda dengan cita rasa manis gurih yang memikat.",
                location = "Jl. Demang Lebar Daun No.36, 20 Ilir D. I, Kec. Ilir Tim. I, Kota Palembang, Sumatera Selatan 30151",
                urlLocation = "https://maps.app.goo.gl/u3DG4ruz3tQAVto9A"
            ), Menu(
                name = "Ayam Goreng",
                price = 40000.0,
                imagePictUrl = "https://ik.imagekit.io/tvlk/blog/2022/08/Resep-Ayam-Goreng-Traveloka-Mart-2.jpg",
                description = "Kulit ayam yang renyah dan daging yang juicy, digoreng hingga kecokelatan sempurna, memberikan sensasi renyah di luar dan lembut di dalam yang tak terlupakan, sebuah kelezatan klasik yang selalu memikat.",
                location = "Jl. Letkol iskandar No. 452 F-G, 15 Ilir, Segaran dempo, Kota Palembang, Sumatera Selatan 30111",
                urlLocation = "https://maps.app.goo.gl/A4PknAQ2GnsHovno8"
            ), Menu(
                name = "Ayam Geprek",
                price = 40000.0,
                imagePictUrl = "https://static.promediateknologi.id/crop/0x0:0x0/750x500/webp/photo/p1/364/2023/09/02/resep-sambal-ayam-geprek-ala-bensu-813204126.jpg",
                description = "Potongan ayam goreng yang digeprek hingga hancur dan disiram dengan sambal pedas yang meletup, menciptakan perpaduan tekstur renyah dan pedas yang menggugah selera, membawa pengalaman rasa yang tak terlupakan.",
                location = "Jl. Ariodillah, 20 Ilir D. III, Kec. Ilir Tim. I, Kota Palembang, Sumatera Selatan 30128",
                urlLocation = "https://maps.app.goo.gl/sVw97SFxJtt2WQ7q8"
            ), Menu(
                name = "Sate Usus Ayam",
                price = 5000.0,
                imagePictUrl = "https://cdn.idntimes.com/content-images/community/2024/02/whatsapp-image-2024-02-26-at-092930-e6b2230e908fbc72b6db8959e2c8d830-c9a2d1cf2047640eeda77cb4d1b3851d_600x400.jpeg",
                description = "Tusukan sate yang dipenuhi dengan usus ayam yang lembut dan diberi bumbu rempah yang meresap, dipanggang hingga kecokelatan dengan aroma harum yang menggoda, menciptakan sajian sate yang lezat dan menggigit.",
                location = "Jl. Angkatan 66 No.1952, Pipa Jaya, Kec. Kemuning, Kota Palembang, Sumatera Selatan 30127",
                urlLocation = "https://maps.app.goo.gl/mQHCn8ZiDZGpjk3AA"
            ), Menu(
                name = "Nasi Padang",
                price = 20000.0,
                imagePictUrl = "https://cdn.rri.co.id/berita/1/images/1689391542821-images_(22)/1689391542821-images_(22).jpeg",
                description = "Penggabungan rasa gurih nasi dan keragaman lauk khas Minangkabau menghasilkan perpaduan cita rasa yang memikat, mengajak lidah menjelajahi petualangan kuliner Indonesia yang tiada duanya.",
                location = "Jl. Nusantara, Timbangan, Kecamatan Indralaya Utara, Kabupaten Ogan Ilir, Sumatera Selatan 30862",
                urlLocation = "https://maps.app.goo.gl/si2T4JhD56f3ejyK8"
            ), Menu(
                name = "Rendang",
                price = 25000.0,
                imagePictUrl = "https://www.warisankuliner.com/gfx/recipes/temp_thumb-1658299943.jpg",
                description = "Tusukan sate yang dipenuhi dengan usus ayam yang lembut dan diberi bumbu rempah yang meresap, dipanggang hingga kecokelatan dengan aroma harum yang menggoda, menciptakan sajian sate yang lezat dan menggigit.",
                location = "Jl. Nusantara No.Km.32, Timbangan, Kecamatan Indralaya Utara, Kabupaten Ogan Ilir, Sumatera Selatan 30862",
                urlLocation = "https://maps.app.goo.gl/8G5PRhgtQRoCcZSK6"
            )
        )
    }
}