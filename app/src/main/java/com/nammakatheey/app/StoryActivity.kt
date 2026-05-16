package com.nammakatheey.app

import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.button.MaterialButton
import com.nammakatheey.app.data.model.StoryPage
import java.util.Locale

class StoryActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var vpStory: ViewPager2
    private lateinit var tvPageIndicator: TextView
    private lateinit var btnPrev: MaterialButton
    private lateinit var btnNext: MaterialButton
    private lateinit var tts: TextToSpeech
    private var isTTSReady = false
    private var storyPages = listOf<StoryPage>()
    private var heroName = "Hero"
    private var heroDistrict = "Karnataka"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story)

        // Get hero details
        heroName = intent.getStringExtra("hero_name") ?: "Hero"
        heroDistrict = intent.getStringExtra("hero_district") ?: "Karnataka"

        // Initialize TTS
        tts = TextToSpeech(this, this)

        // Set hero name in header
        findViewById<TextView>(R.id.tvStoryHeroName).text = heroName

        // Back button
        findViewById<ImageButton>(R.id.btnStoryBack).setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Get story pages
        storyPages = getStoryPages(heroName)

        // Setup ViewPager2
        vpStory = findViewById(R.id.vpStory)
        tvPageIndicator = findViewById(R.id.tvPageIndicator)
        btnPrev = findViewById(R.id.btnPrevPage)
        btnNext = findViewById(R.id.btnNextPage)

        vpStory.adapter = StoryPagerAdapter(storyPages)
        updatePageIndicator(0)

        vpStory.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                updatePageIndicator(position)
            }
        })

        // Previous button
        btnPrev.setOnClickListener {
            if (vpStory.currentItem > 0) {
                vpStory.currentItem -= 1
            }
        }

        // Next button
        btnNext.setOnClickListener {
            if (vpStory.currentItem < storyPages.size - 1) {
                vpStory.currentItem += 1
            } else {
                goToQuiz()
            }
        }

        // TTS button
        findViewById<ImageButton>(R.id.btnTTS).setOnClickListener {
            if (isTTSReady) {
                tts.speak(storyPages[vpStory.currentItem].text, TextToSpeech.QUEUE_FLUSH, null, null)
            }
        }
    }

    private fun goToQuiz() {
        val quizIntent = Intent(this, QuizActivity::class.java)
        quizIntent.putExtra("hero_name", heroName)
        quizIntent.putExtra("hero_district", heroDistrict)
        startActivity(quizIntent)
    }

    private fun updatePageIndicator(position: Int) {
        tvPageIndicator.text = "Page ${position + 1} of ${storyPages.size}"
        btnPrev.isEnabled = position > 0
        btnNext.text = if (position == storyPages.size - 1) "Take Quiz →" else "Next →"
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts.language = Locale.ENGLISH
            isTTSReady = true
        }
    }

    override fun onDestroy() {
        tts.stop()
        tts.shutdown()
        super.onDestroy()
    }

    private fun getStoryPages(heroName: String): List<StoryPage> {
        return when (heroName) {
            "Sangolli Rayanna" -> listOf(
                StoryPage("The Brave Warrior", "Sangolli Rayanna was born in 1798 in Sangolli village of Belagavi district. He was a brave warrior who fought fearlessly against British rule in India."),
                StoryPage("Loyal General", "Rayanna was the army chief of Kittur Chennamma. He was known for his exceptional courage, military skills, and deep loyalty to his queen and motherland."),
                StoryPage("The Rebellion", "After Kittur fell to the British, Rayanna did not give up. He continued guerrilla warfare against the British forces, inspiring many locals to join the fight for freedom."),
                StoryPage("The Sacrifice", "In 1831, Rayanna was captured by the British due to a betrayal. He was hanged on March 26, 1831, becoming a martyr for India's freedom struggle."),
                StoryPage("The Legacy", "Today Sangolli Rayanna is remembered as a great hero of Karnataka. His statue stands tall in Belagavi, and his courage continues to inspire generations.")
            )
            "Kittur Chennamma" -> listOf(
                StoryPage("The Queen of Kittur", "Kittur Chennamma was born in 1778 in Kakati village. She became the Queen of Kittur kingdom in present-day Belagavi district of Karnataka."),
                StoryPage("Against the British", "When the British tried to annex Kittur under the Doctrine of Lapse in 1824, Chennamma bravely refused and took up arms against them."),
                StoryPage("The Victory", "In October 1824, Chennamma led her army and defeated the British forces. She even captured two British officers, making her one of the first rulers to defeat the British in battle."),
                StoryPage("The Defeat", "The British returned with a larger army. Despite her brave resistance, Kittur fell and Chennamma was captured and imprisoned at Bailhongal fort."),
                StoryPage("Eternal Inspiration", "Kittur Chennamma died in prison in 1829. She is remembered as one of India's first freedom fighters. Her statue stands in the Indian Parliament as a symbol of courage.")
            )
            "Tipu Sultan" -> listOf(
                StoryPage("The Tiger of Mysore", "Tipu Sultan was born on November 20, 1750 in Devanahalli. He became the ruler of the Kingdom of Mysore and was known as the Tiger of Mysore."),
                StoryPage("Brave Warrior", "Tipu Sultan was one of the most powerful rulers of his time. He fought four major wars against the British East India Company and won many battles."),
                StoryPage("Modern Innovations", "Tipu Sultan was ahead of his time. He introduced rocket artillery in warfare, developed trade, and built a strong navy. He also planted trees and built roads."),
                StoryPage("The Final Battle", "In the Fourth Anglo-Mysore War in 1799, the British along with their allies attacked Srirangapatna. Tipu Sultan fought till his last breath defending his capital."),
                StoryPage("The Legacy", "Tipu Sultan died on May 4, 1799, defending his kingdom. He is remembered as a great freedom fighter and administrator who never bowed to the British.")
            )
            "Kuvempu" -> listOf(
                StoryPage("The People's Poet", "Kuvempu, born Kuppali Venkatappa Puttappa in 1904, was one of Karnataka's greatest poets and writers. He was born in Kuppali village of Shivamogga district."),
                StoryPage("Literary Giant", "Kuvempu wrote extensively in Kannada. His epic poem Ramayana Darshanam won him the Jnanpith Award in 1967, the highest literary honour in India."),
                StoryPage("Vishwamanava", "Kuvempu believed in the concept of Vishwamanava — the Universal Human Being — transcending caste, religion, and region. He fought against social discrimination."),
                StoryPage("Karnataka Ratna", "Kuvempu was awarded Karnataka Ratna, the highest civilian honour of Karnataka. He also wrote the Karnataka state anthem Jaya Bharata Jananiya Tanujate."),
                StoryPage("The Legacy", "Kuvempu passed away in 1994. His home in Kuppali is now a museum. He remains the most celebrated literary figure in Karnataka's modern history.")
            )
            "Onake Obavva" -> listOf(
                StoryPage("The Brave Woman", "Onake Obavva was a brave woman who lived in Chitradurga in the 18th century. Her husband was a guard at the Chitradurga fort under the Nayaka kings."),
                StoryPage("The Discovery", "One day while her husband was away, Obavva discovered that Hyder Ali's soldiers were secretly entering the fort through a small hole in the wall called Kindi."),
                StoryPage("The Fight", "With no time to alert anyone, Obavva single-handedly killed the enemy soldiers one by one using her Onake — a long wooden pestle used for pounding — as they entered through the hole."),
                StoryPage("The Sacrifice", "Obavva fought bravely until she herself fell. By the time her husband returned, she had sacrificed her life protecting the fort from the enemy invasion."),
                StoryPage("The Legacy", "Onake Obavva is celebrated as a symbol of courage and patriotism in Karnataka. A statue of her stands at Chitradurga fort, and she is remembered as one of Karnataka's greatest heroes.")
            )
            "Kempe Gowda I" -> listOf(
                StoryPage("The Visionary", "Kempe Gowda I was born in 1510 and served as a chieftain under the Vijayanagara Empire. He had a grand vision of building a new city in the region."),
                StoryPage("Founding Bengaluru", "In 1537, Kempe Gowda laid the foundation of Bengaluru — today's Bangalore, the IT capital of India. He built a mud fort and established a marketplace."),
                StoryPage("The Four Towers", "To mark the boundary of his city, Kempe Gowda built four watchtowers at the four corners of Bengaluru. These towers still exist today as historical monuments."),
                StoryPage("City Builder", "Kempe Gowda built many temples, tanks, and roads in Bengaluru. He planned the city with great foresight, creating a layout that supported trade and community living."),
                StoryPage("The Legacy", "Kempe Gowda I is honoured as the founder of Bengaluru. A grand statue of him stands at Kempegowda International Airport, welcoming visitors to the city he built.")
            )
            "Sir M. Visvesvaraya" -> listOf(
                StoryPage("The Engineer", "Sir Mokshagundam Visvesvaraya was born on September 15, 1861 in Muddenahalli, Chikkaballapura district. He became one of India's greatest engineers and statesmen."),
                StoryPage("Engineering Marvel", "Visvesvaraya designed the flood protection system for Hyderabad and the Krishna Raja Sagara dam in Mysuru — one of the largest reservoirs in Asia at the time."),
                StoryPage("Diwan of Mysore", "As the Diwan of Mysore from 1912 to 1918, Visvesvaraya transformed the state. He set up industries, improved education, and modernised Mysore's administration."),
                StoryPage("Bharat Ratna", "Visvesvaraya was awarded the Bharat Ratna in 1955 — India's highest civilian honour. His birthday, September 15, is celebrated as Engineers Day across India."),
                StoryPage("The Legacy", "Sir M. Visvesvaraya lived to be 101 years old. He is remembered as the father of modern Karnataka — a visionary who transformed the state through engineering and education.")
            )
            "Basavanna" -> listOf(
                StoryPage("The Social Reformer", "Basavanna was born in 1131 in Bagewadi, present-day Vijayapura district. He was a 12th century philosopher, statesman, and social reformer."),
                StoryPage("The Lingayat Movement", "Basavanna founded the Lingayat movement, which challenged the rigid caste system and promoted equality among all people regardless of birth or social status."),
                StoryPage("Anubhava Mantapa", "Basavanna established the Anubhava Mantapa — a spiritual parliament — where people of all castes and backgrounds could come together to discuss philosophy and social issues."),
                StoryPage("Vachanas", "Basavanna composed Vachanas — short spiritual poems in Kannada — that spoke of equality, devotion, and social justice. These are still read and sung across Karnataka today."),
                StoryPage("The Legacy", "Basavanna died around 1167. He is revered as a saint and social reformer. His teachings of Kayaka — dignified work — and Dasoha — sharing — continue to inspire millions.")
            )
            "Keladi Chennamma" -> listOf(
                StoryPage("The Warrior Queen", "Keladi Chennamma was the Queen of the Keladi Kingdom in present-day Shivamogga district. She ruled in the late 17th century with great courage and wisdom."),
                StoryPage("Defying Aurangzeb", "When Mughal Emperor Aurangzeb demanded she hand over Rajaram, the son of Chhatrapati Shivaji who had sought refuge in her kingdom, Chennamma boldly refused."),
                StoryPage("The Brave Stand", "Chennamma declared she would protect Rajaram at any cost. She strengthened her army and prepared to fight the mighty Mughal forces if needed. Aurangzeb eventually backed down."),
                StoryPage("Wise Administrator", "Chennamma was not just a warrior but also a skilled administrator. She built temples, managed the kingdom's finances well, and was loved by her people."),
                StoryPage("The Legacy", "Keladi Chennamma is celebrated as one of Karnataka's bravest queens. She is remembered for her courage in standing up to one of the most powerful emperors of her time.")
            )
            "Madhvacharya" -> listOf(
                StoryPage("The Philosopher", "Madhvacharya was born in 1238 in Pajaka village near Udupi. He became one of India's greatest philosophers and the founder of the Dvaita Vedanta school of thought."),
                StoryPage("Dvaita Philosophy", "Madhvacharya's Dvaita philosophy teaches that God and the individual soul are eternally distinct. This was a revolutionary idea that challenged the Advaita philosophy of Adi Shankaracharya."),
                StoryPage("The Udupi Krishna Temple", "Madhvacharya established the famous Krishna temple in Udupi and the Ashta Mathas — eight monasteries — that continue to manage the temple to this day."),
                StoryPage("Prolific Writer", "Madhvacharya wrote 37 works in Sanskrit covering philosophy, devotion, and logic. He travelled across India debating scholars and spreading his philosophy."),
                StoryPage("The Legacy", "Madhvacharya is believed to have lived until 1317. He is revered as a saint and philosopher whose Dvaita Vedanta continues to influence millions of followers worldwide.")
            )
            "Gangubai Hangal" -> listOf(
                StoryPage("Early Life", "Gangubai Hangal was born in 1913 in Dharwad. She came from a humble background but showed extraordinary talent in Hindustani classical music from a very young age."),
                StoryPage("The Voice of Dharwad", "Gangubai mastered the Kirana gharana style of Hindustani classical music. Her deep, powerful voice was unique and instantly recognizable across India."),
                StoryPage("Breaking Barriers", "Despite facing poverty and social stigma, Gangubai never gave up on her music. She performed at major concerts and became one of India's most respected classical singers."),
                StoryPage("Recognition", "Gangubai received the Padma Vibhushan — India's second highest civilian honour. She also received the Sangeet Natak Akademi Award for her lifetime contribution to music."),
                StoryPage("The Legacy", "Gangubai Hangal passed away in 2009 at the age of 96. She is remembered as one of Karnataka's greatest musicians and an inspiration to women across India.")
            )
            "Kampanna Udaiyar" -> listOf(
                StoryPage("The Warrior King", "Kampanna Udaiyar was a prince of the Vijayanagara Empire who ruled in the 14th century. He was known for his military campaigns across South India."),
                StoryPage("Liberator of the South", "Kampanna led a successful military campaign to liberate Tamil Nadu from the Sultanate of Madurai. He defeated the Sultan and restored Hindu temples that had been destroyed."),
                StoryPage("The Legacy", "Kampanna Udaiyar is remembered as a brave warrior who expanded the Vijayanagara Empire and protected the culture and traditions of South India.")
            )
            "Dodda Kempe Gowda" -> listOf(
                StoryPage("The Administrator", "Dodda Kempe Gowda was a chieftain who served under the Vijayanagara Empire in the 16th century. He was known for his wise administration of the Bengaluru region."),
                StoryPage("Building the City", "Following the vision of Kempe Gowda I, Dodda Kempe Gowda continued developing Bengaluru. He built tanks, temples, and roads that helped the city grow."),
                StoryPage("The Legacy", "Dodda Kempe Gowda is remembered as one of the key figures in the early development of Bengaluru, continuing the legacy of his predecessor Kempe Gowda I.")
            )
            "Kadamba Mayurasharma" -> listOf(
                StoryPage("The Founder", "Mayurasharma founded the Kadamba dynasty around 345 AD, making it one of the earliest native kingdoms of Karnataka. He was born in Talagunda near Shivamogga."),
                StoryPage("From Scholar to Warrior", "Mayurasharma originally went to Kanchi to study but was insulted by Pallava guards. Filled with anger, he took up arms and trained an army to fight back."),
                StoryPage("Victory and Kingdom", "Mayurasharma defeated the Pallavas and established his kingdom in Banavasi, present-day Uttara Kannada. The Kadamba dynasty became the first independent kingdom of Karnataka."),
                StoryPage("The Legacy", "Mayurasharma is celebrated as the father of Karnataka's political independence. The Kadamba dynasty he founded laid the foundation for Karnataka's rich cultural and political history.")
            )
            "Rukmangada" -> listOf(
                StoryPage("The Just King", "Rukmangada was a legendary king mentioned in ancient texts. He was known for his absolute commitment to truth, justice, and dharma above all else."),
                StoryPage("Test of Devotion", "According to legend, Rukmangada faced a divine test where he had to choose between his devotion to God and his own son. He chose his devotion, showing extraordinary courage."),
                StoryPage("The Legacy", "Rukmangada is remembered as a symbol of righteousness and devotion. His story is told across Karnataka as an example of moral courage and commitment to truth.")
            )
            "Madakari Nayaka" -> listOf(
                StoryPage("The Last Nayaka", "Madakari Nayaka was the last ruler of the Nayaka dynasty of Chitradurga. He ruled from 1758 to 1779 and was known for his fierce resistance against Hyder Ali."),
                StoryPage("Defender of Chitradurga", "When Hyder Ali's forces attacked Chitradurga, Madakari Nayaka led a brave defence of the fort. He fought multiple battles against the powerful Mysore army."),
                StoryPage("The Fall", "Despite his brave resistance, Chitradurga eventually fell to Hyder Ali in 1779. Madakari Nayaka was captured and the Nayaka dynasty came to an end."),
                StoryPage("The Legacy", "Madakari Nayaka is remembered as a brave defender of Karnataka's independence. His courage in facing the mighty forces of Hyder Ali makes him a celebrated hero of Chitradurga.")
            )
            "Kota Shivaram Karanth" -> listOf(
                StoryPage("The Polymath", "Kota Shivaram Karanth was born in 1902 in Kota village of Udupi district. He was a remarkable writer, filmmaker, environmentalist, and Yakshagana artist."),
                StoryPage("Literary Giant", "Karanth wrote over 500 books including novels, plays, and children's books. His novel Chomana Dudi won him the Jnanpith Award in 1977 — the highest literary honour in India."),
                StoryPage("Environmentalist", "Karanth was a passionate environmentalist long before it became popular. He fought against the destruction of forests and was a vocal critic of the Silent Valley hydroelectric project."),
                StoryPage("Returned Padma Bhushan", "In protest against the government's nuclear policy, Karanth returned his Padma Bhushan award — a rare act of courage that showed his strong principles."),
                StoryPage("The Legacy", "Kota Shivaram Karanth passed away in 1997. He is remembered as one of Karnataka's greatest intellectuals — a man of extraordinary talent and uncompromising integrity.")
            )
            "U.R. Ananthamurthy" -> listOf(
                StoryPage("The Writer", "U.R. Ananthamurthy was born in 1932 in Melige village of Shivamogga district. He became one of Karnataka's most celebrated novelists and thinkers."),
                StoryPage("Samskara", "His most famous novel Samskara, published in 1965, challenged caste discrimination and orthodox Brahmin society. It was later made into an award-winning Kannada film."),
                StoryPage("Jnanpith Award", "Ananthamurthy received the Jnanpith Award in 1994 for his outstanding contribution to Kannada literature. He was also a professor and public intellectual."),
                StoryPage("Social Thinker", "Ananthamurthy was not just a writer but a bold social commentator. He spoke fearlessly about caste, religion, and politics in India throughout his life."),
                StoryPage("The Legacy", "U.R. Ananthamurthy passed away in 2014. He is remembered as a literary giant who used his pen to fight for equality, justice, and a more humane society in India.")
            )
            "Shantala Devi" -> listOf(
                StoryPage("The Queen Dancer", "Shantala Devi was the queen of the Hoysala King Vishnuvardhana in the 12th century. She was renowned across Karnataka for her extraordinary beauty and dancing ability."),
                StoryPage("Master of Dance", "Shantala was a master of Bharatanatyam and other classical dance forms. She performed at the royal court and at temples, enchanting all who witnessed her art."),
                StoryPage("Belur Temple", "The famous Belur Chennakeshava Temple in Hassan district has sculptures believed to be modelled after Shantala Devi. These sculptures are considered masterpieces of Hoysala art."),
                StoryPage("The Legacy", "Shantala Devi is celebrated as a symbol of Karnataka's rich cultural heritage. Her story inspired the Kannada novel Shantala by S.L. Bhyrappa, one of Karnataka's greatest novels.")
            )
            "Vishnuvardhana" -> listOf(
                StoryPage("The Hoysala King", "Vishnuvardhana was a great king of the Hoysala dynasty who ruled from 1108 to 1152. He transformed the Hoysala kingdom into a major power in South India."),
                StoryPage("Conversion Story", "Vishnuvardhana was originally a Jain but converted to Vaishnavism under the influence of the saint Ramanujacharya. He became a devoted follower and built many Vishnu temples."),
                StoryPage("Belur and Halebidu", "Vishnuvardhana built the magnificent Chennakeshava Temple at Belur and the Hoysaleshwara Temple at Halebidu. These temples are UNESCO World Heritage Sites and architectural wonders."),
                StoryPage("Military Victories", "Vishnuvardhana defeated the Cholas and expanded the Hoysala kingdom significantly. He was a skilled military commander as well as a patron of art and architecture."),
                StoryPage("The Legacy", "Vishnuvardhana is remembered as one of Karnataka's greatest kings. The temples he built stand as eternal monuments to his devotion and vision for Karnataka's cultural heritage.")
            )
            "Sharif Saheb" -> listOf(
                StoryPage("The Saint Poet", "Shishunala Sharif Saheb was born in 1819 in Shishunala village of Haveri district. He was a Sufi saint and one of Karnataka's most beloved poets."),
                StoryPage("Unity in Diversity", "Sharif Saheb's poetry celebrated the unity of all religions. His guru was a Hindu Brahmin named Govinda Bhatta, and their friendship became a symbol of Hindu-Muslim unity in Karnataka."),
                StoryPage("The Vachanas", "Sharif Saheb composed hundreds of Vachanas — devotional poems — in Kannada that spoke of love, equality, and devotion to God beyond religious boundaries."),
                StoryPage("Simple Life", "Despite his fame, Sharif Saheb lived a simple life. He travelled across Karnataka singing his songs and spreading his message of love and unity among all people."),
                StoryPage("The Legacy", "Sharif Saheb passed away in 1888. He is remembered as a saint who embodied the true spirit of Karnataka — a land where different religions have always lived in harmony.")
            )
            "S. Nijalingappa" -> listOf(
                StoryPage("The Political Leader", "S. Nijalingappa was born in 1902 in Chickmagalur. He was one of Karnataka's most prominent political leaders and a key figure in the Indian National Congress party."),
                StoryPage("Freedom Fighter", "Nijalingappa actively participated in India's freedom struggle. He was imprisoned multiple times by the British for his participation in the independence movement."),
                StoryPage("Chief Minister", "Nijalingappa served as the Chief Minister of Mysore state twice. Under his leadership, Karnataka saw significant development in education, agriculture, and infrastructure."),
                StoryPage("Congress President", "Nijalingappa served as the President of the Indian National Congress from 1968 to 1969 — one of the highest positions in Indian politics at the time."),
                StoryPage("The Legacy", "S. Nijalingappa passed away in 2000. He is remembered as a dedicated freedom fighter and statesman who devoted his entire life to the service of Karnataka and India.")
            )
            "Puttanna Chetty" -> listOf(
                StoryPage("The Social Reformer", "T. Puttanna Chetty was born in 1862 in Bengaluru. He was a prominent social reformer, businessman, and political leader in early 20th century Karnataka."),
                StoryPage("Champion of Education", "Puttanna Chetty was a strong advocate for education, especially for women and lower castes. He established schools and worked to make education accessible to all."),
                StoryPage("Political Leader", "Puttanna Chetty was elected to the Mysore Legislative Council and used his position to fight for the rights of ordinary people against the privileged classes."),
                StoryPage("The Legacy", "Puttanna Chetty passed away in 1910. He is remembered as a pioneer of social reform in Karnataka who worked tirelessly to create a more equal and just society.")
            )
            "Krishnaraja Wadiyar IV" -> listOf(
                StoryPage("The Benevolent King", "Krishnaraja Wadiyar IV was born in 1884 and became the Maharaja of Mysore at a young age. He is remembered as one of the most enlightened rulers in Indian history."),
                StoryPage("Modern Mysore", "Under his reign, Mysore became one of the most progressive states in India. He invited Sir M. Visvesvaraya to modernise the state and built dams, hospitals, and universities."),
                StoryPage("Patron of Arts", "Krishnaraja Wadiyar was a great patron of art, music, and culture. The famous Mysuru Dasara festival was elevated to its grand current form during his reign."),
                StoryPage("People's King", "Mahatma Gandhi called Krishnaraja Wadiyar a Rajarshi — a saintly king. He was loved by his people for his kindness, wisdom, and genuine care for their welfare."),
                StoryPage("The Legacy", "Krishnaraja Wadiyar IV passed away in 1940. He is remembered as the king who transformed Mysore into a model state and one of the most progressive kingdoms in India.")
            )
            "Kanakadasa" -> listOf(
                StoryPage("The Saint Poet", "Kanakadasa was born around 1509 in Bada village of Haveri district. He was a great saint, poet, and musician who composed beautiful devotional songs in Kannada."),
                StoryPage("Against Caste", "Kanakadasa was born into a lower caste community but his devotion to Lord Krishna was unmatched. He faced discrimination but never let it stop his spiritual journey."),
                StoryPage("The Miracle at Udupi", "According to legend, when Kanakadasa was not allowed to enter the Udupi Krishna temple due to his caste, Lord Krishna himself turned to face him — creating a window in the temple wall. This window is called Kanakana Kindi."),
                StoryPage("Keertanas", "Kanakadasa composed hundreds of Keertanas — devotional songs — that are still sung across Karnataka today. His compositions are part of Karnataka's classical music tradition."),
                StoryPage("The Legacy", "Kanakadasa passed away around 1609. He is revered as one of Karnataka's greatest saints and poets. His life is a powerful message that devotion and character matter more than birth.")
            )
            "Siddalingaiah" -> listOf(
                StoryPage("The Dalit Poet", "Siddalingaiah was born in 1954 in Magadi, Ramanagara district. He became one of Karnataka's most powerful poets and a pioneering voice for Dalit rights and dignity."),
                StoryPage("Janamana", "His poetry collection Janamana published in 1975 shook Karnataka's literary world. His poems spoke raw, powerful truths about the pain and dignity of Dalit life in India."),
                StoryPage("Political Life", "Siddalingaiah served as a member of the Karnataka Legislative Council. He used his political position to fight for the rights of Dalits and marginalised communities."),
                StoryPage("Kannada Sahitya Sammelana", "Siddalingaiah served as the president of the Kannada Sahitya Sammelana — the highest honour in Kannada literary circles. He was beloved by people across all communities."),
                StoryPage("The Legacy", "Siddalingaiah passed away in 2021. He is remembered as the poet of the oppressed — a man who gave voice to the voiceless and fought his entire life for a more equal Karnataka.")
            )
            "Thippaiah" -> listOf(
                StoryPage("The Freedom Fighter", "Thippaiah was a brave freedom fighter from Karnataka who participated in the struggle against British rule in the 19th century."),
                StoryPage("Local Resistance", "Thippaiah organised local resistance against British taxation and oppression. He inspired ordinary people in his region to stand up for their rights and dignity."),
                StoryPage("The Legacy", "Thippaiah is remembered as a local hero who fought for the freedom of his people. His courage in standing up to the British colonial power makes him an inspiration for Karnataka.")
            )
            "Akka Mahadevi" -> listOf(
                StoryPage("The Saint Poet", "Akka Mahadevi was a 12th century saint and poet from Karnataka. She was born in Udutadi village and became one of the most important figures in the Veerashaiva movement."),
                StoryPage("Devotion to Shiva", "Akka Mahadevi was completely devoted to Lord Shiva whom she called Chenna Mallikarjuna — her true husband. She rejected worldly attachments and lived a life of pure devotion."),
                StoryPage("The Vachanas", "Akka Mahadevi composed hundreds of Vachanas — devotional poems — that are masterpieces of Kannada literature. Her poetry speaks of spiritual longing, freedom, and love for God."),
                StoryPage("Breaking Norms", "Akka Mahadevi challenged social norms of her time. She left her marriage to a king when he tried to control her devotion and wandered freely as a spiritual seeker."),
                StoryPage("The Legacy", "Akka Mahadevi is celebrated as one of Karnataka's greatest saints and poets. She is a symbol of spiritual courage and women's independence, revered across Karnataka to this day.")
            )
            "General Cariappa" -> listOf(
                StoryPage("Early Life", "Field Marshal K.M. Cariappa was born on January 28, 1899 in Shanivarsanthe, Kodagu district. He joined the British Indian Army and rose through the ranks with exceptional skill."),
                StoryPage("First Indian Commander", "On January 15, 1949, Cariappa became the first Indian to command the Indian Army as Commander-in-Chief, taking over from the British General Sir Francis Butcher. January 15 is now celebrated as Army Day."),
                StoryPage("1947 War", "Cariappa commanded the Indian forces on the Western Front during the Indo-Pakistani War of 1947. His leadership was crucial in protecting Kashmir for India."),
                StoryPage("Field Marshal", "Cariappa was awarded the rank of Field Marshal — the highest rank in the Indian Army — in 1986. He is one of only two officers to have received this honour."),
                StoryPage("The Legacy", "General Cariappa passed away in 1993. He is remembered as a great soldier, a man of integrity, and a proud son of Karnataka who served India with distinction throughout his life.")
            )
            "Mahmud Gawan" -> listOf(
                StoryPage("The Scholar", "Mahmud Gawan was born in 1411 in Persia. He came to the Bahmani Sultanate in Bidar as a merchant but rose to become its most powerful and respected minister."),
                StoryPage("Prime Minister of Bidar", "Gawan became the Prime Minister of the Bahmani Sultanate and administered it with extraordinary efficiency and fairness for decades. He was known for his justice and wisdom."),
                StoryPage("Champion of Education", "Gawan built the famous Madrasa of Mahmud Gawan in Bidar — a grand college that attracted scholars from across the Islamic world. It remains one of the finest examples of Bahmani architecture."),
                StoryPage("The Tragedy", "Despite his loyal service, Gawan was falsely accused of treason by jealous rivals. The Sultan ordered his execution in 1481 — a tragic end for one of the Deccan's greatest statesmen."),
                StoryPage("The Legacy", "Mahmud Gawan is remembered as one of the most enlightened administrators in Karnataka's medieval history. His madrasa in Bidar stands as a monument to his love of knowledge and learning.")
            )
            else -> listOf(
                StoryPage("A Local Hero", "This hero from Karnataka dedicated their life to the service of the people and the freedom of the nation. Their courage and sacrifice changed the course of history."),
                StoryPage("Their Contribution", "Through courage, sacrifice, and determination, this hero made a lasting impact on Karnataka's history and culture. Their deeds are remembered to this day."),
                StoryPage("The Legacy", "Their story continues to inspire generations of Kannadigas to be proud of their heritage and stand up for what is right. Karnataka will always remember their sacrifice.")
            )
        }
    }
}