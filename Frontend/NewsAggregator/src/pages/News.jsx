import { useEffect, useState } from "react";
import NewsService from "../services/newsService";
import { Link } from "react-router-dom";

function News() {
    const [news, setNews] = useState([]); // ✅ Always an array
    const [displayedNewsCount, setDisplayedNewsCount] = useState(20);
    const [selectedPublisher, setSelectedPublisher] = useState("");

    useEffect(() => {
        let newsService = new NewsService();
        newsService.getAllNews()
            .then(result => setNews(result.data?.data || [])) // ✅ Ensure data is an array
            .catch(error => console.error("Error fetching news:", error));
    }, []);

    const showMoreNews = () => {
        setDisplayedNewsCount(prevCount => prevCount + 20);
    };

    // ✅ Ensure filtering works even if `news` is empty
    const filteredNews = selectedPublisher
        ? news.filter(item => item.publisher === selectedPublisher)
        : news;

    const getPublishers = () => {
        if (!Array.isArray(news)) return []; // ✅ Avoid calling map() on undefined
        const publishers = new Set(news.map(item => item.publisher));
        return Array.from(publishers);
    };

    const publishers = getPublishers();

    return (
        <>
            <div className="flex flex-wrap gap-2 p-4 justify-center">
                {/* All Publishers button */}
                <button
                    onClick={() => setSelectedPublisher("")}
                    className={`px-4 py-2 rounded ${selectedPublisher === "" ? "bg-blue-500 text-white" : "bg-gray-200"}`}
                >
                    All Publishers
                </button>

                {/* Publisher buttons */}
                {publishers.map((publisher, index) => (
                    <button
                        key={index}
                        onClick={() => setSelectedPublisher(publisher)}
                        className={`px-4 py-2 rounded ${selectedPublisher === publisher ? "bg-blue-500 text-white" : "bg-gray-200"}`}
                    >
                        {publisher}
                    </button>
                ))}
            </div>

            <div className="bg-white py-6 sm:py-8 lg:py-12">
                <div className="mx-auto max-w-screen-2xl px-4 md:px-8">
                    <div className="mb-10 md:mb-16">
                        <h2 className="mb-4 text-center text-2xl font-bold text-gray-800 md:mb-6 lg:text-3xl">
                            News
                        </h2>
                    </div>
                </div>
            </div>

            <div className="mx-auto max-w-screen-2xl px-4 md:px-8">
                <div className="grid gap-5 sm:grid-cols-2 md:gap-6 lg:grid-cols-3 xl:grid-cols-4 xl:gap-8">
                    {filteredNews.slice(0, displayedNewsCount).map((item, index) => {
                        const date = new Date(item.publishedDate);
                        const formattedDate = date.toLocaleString("tr-TR", {
                            year: "numeric",
                            month: "long",
                            day: "numeric",
                            hour: "2-digit",
                            minute: "2-digit"
                        });

                        return (
                            <div className="flex flex-col overflow-hidden rounded-lg border bg-white" key={index}>
                                <Link
                                    to={`/news/${item.id}`}
                                    className="group relative block h-48 overflow-hidden bg-gray-100 md:h-64"
                                >
                                    <img
                                        src={item.imageUrl || "/601eeb63-6542-4a1b-b323-a23303f48d55.webp"} // ✅ Removed `/public/`
                                        loading="lazy"
                                        alt={item.title || "News Image"} // ✅ Added alt text
                                        className="absolute inset-0 h-full w-full object-cover object-center transition duration-200 group-hover:scale-110"
                                    />
                                </Link>

                                <div className="flex flex-1 flex-col p-4 sm:p-6">
                                    <h2 className="mb-2 text-lg font-semibold text-gray-800">
                                        <Link to={`/news/${item.id}`}>{item.title}</Link>
                                    </h2>
                                    <p className="mb-8 text-gray-500">
                                        {item.content.length > 200 ? item.content.slice(0, 200) + "..." : item.content}
                                    </p>

                                    <div className="mt-auto flex items-end justify-between">
                                        <div className="flex items-center gap-2">
                                            <div className="h-10 w-10 shrink-0 overflow-hidden rounded-full bg-gray-100">
                                                <img
                                                    src="/601eeb63-6542-4a1b-b323-a23303f48d55.webp" // ✅ Removed `../../public/`
                                                    loading="lazy"
                                                    alt={item.publisher || "Publisher Image"}
                                                    className="h-full w-full object-cover object-center"
                                                />
                                            </div>
                                            <div>
                                                <span className="block text-indigo-500 font-bold">{item.publisher}</span>
                                                <span className="block text-sm text-gray-400 font-medium">
                                                    {formattedDate}
                                                </span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        );
                    })}
                </div>

                {displayedNewsCount < filteredNews.length && (
                    <div className="text-center my-8">
                        <button
                            onClick={showMoreNews}
                            className="px-6 py-2 text-white bg-blue-500 hover:bg-blue-600 rounded"
                        >
                            Daha Fazla Göster
                        </button>
                    </div>
                )}
            </div>
        </>
    );
}

export default News;
