import React, { useState } from 'react';
import { motion } from 'framer-motion';
import { ChevronDown, ChevronUp } from 'lucide-react';


const Faqs = () => {
    const faqs = [
        { q: 'How do I get my Instagram data?', a: 'Go to Instagram Settings > Privacy > Download Your Data.' },
        { q: 'Is my data secure?', a: 'Yes, we use AWS S3 with encryption and never share your data.' },
  ] ;
  const [openFaq, setOpenFaq] = useState(null);


  return (
    <div>
      {/* FAQs */}
      <div className="max-w-3xl mx-auto p-6">
        <h2 className="text-2xl font-semibold text-purple-600 mb-4">Common Questions</h2>
        {faqs.map((faq, index) => (
          <motion.div
            key={index}
            initial={{ opacity: 0, y: 10 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ delay: 0.2 * index }}
            className="mb-2"
          >
            <button
              className="w-full flex justify-between items-center p-4 bg-gray-50 rounded-md"
              onClick={() => setOpenFaq(openFaq === index ? null : index)}
            >
              <span className="text-gray-800">{faq.q}</span>
              {openFaq === index ? <ChevronUp className="text-blue-600" /> : <ChevronDown className="text-blue-600" />}
            </button>
            {openFaq === index && (
              <div className="p-4 bg-white rounded-md shadow-md">
                <p className="text-gray-600">{faq.a}</p>
              </div>
            )}
          </motion.div>
        ))}
      </div>
    </div>
  )
}

export default Faqs
