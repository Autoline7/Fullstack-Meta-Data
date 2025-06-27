
import React from 'react';
import { Link } from 'react-router-dom';
import { motion } from 'framer-motion';
import { Upload, BarChart, CreditCard } from 'lucide-react';
import Faqs from '@/components/Faqs';
import Navbar from '@/components/Navbar';

const GetStarted = () => {
  var isAuthenticated = !!localStorage.getItem('token');
  isAuthenticated = true;

  return (
    <div className="min-h-screen bg-white">
        <div className="fixed w-full z-50">
            <Navbar />
        </div>
        
      {/* Hero */}
      <motion.div
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        className="max-w-4xl mx-auto mt-16 p-6 text-center"
      >
        <h1 className="text-4xl font-bold text-purple-600 mb-2">UnfollowSpy</h1>
        <p className="text-xl text-blue-600 mb-6">Reveal the Unfollowers, Own Your Feed!</p>
        <Link
          to={isAuthenticated ? '/dashboard' : '/signup'}
          className="inline-block px-6 py-3 bg-blue-600 text-white rounded-md hover:bg-purple-600 transition"
        >
          Go to You Dashboard
        </Link>
      </motion.div>

      {/* Features */}
      <div className="max-w-6xl mx-auto p-6">
        <h2 className="text-2xl font-semibold text-purple-600 text-center mb-8">Why UnfollowSpy?</h2>
        <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
          {[
            { icon: Upload, title: 'Easy Upload', desc: 'Upload your Instagram JSON in seconds.' },
            { icon: BarChart, title: 'Clear Insights', desc: 'See unfollowers and stats instantly.' },
            { icon: CreditCard, title: 'Premium Option', desc: 'Save history with a subscription.' },
          ].map((feature, index) => (
            <motion.div
              key={index}
              initial={{ opacity: 0, y: 20 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ delay: 0.2 * index }}
              className="p-6 bg-gray-50 rounded-lg shadow-md text-center"
            >
              <feature.icon className="w-12 h-12 text-blue-600 mx-auto mb-4" />
              <h3 className="text-lg font-medium text-gray-800">{feature.title}</h3>
              <p className="text-gray-600">{feature.desc}</p>
            </motion.div>
          ))}
        </div>
      </div>

      {/* Testimonial */}
      <motion.div
        initial={{ opacity: 0, x: 20 }}
        animate={{ opacity: 1, x: 0 }}
        className="max-w-2xl mx-auto p-6"
      >
        <div className="p-6 bg-white border border-purple-600 rounded-lg shadow-md">
          <p className="text-lg italic text-gray-700">
            “UnfollowSpy helped me clean up my Instagram feed in minutes! Super easy to use.”
          </p>
          <p className="text-right text-blue-600 font-medium mt-2">- Jane D.</p>
        </div>
      </motion.div>

      {/* CTA */}
      <div className="max-w-4xl mx-auto p-6 text-center">
        <Link
          to={isAuthenticated ? '/upload' : '/signup'}
          className="inline-block px-6 py-3 bg-purple-600 text-white rounded-md hover:bg-blue-600 transition"
        >
          Try It Now
        </Link>
      </div>

      {/* FAQS */}
      <Faqs />
    </div>
  );
};

export default GetStarted;