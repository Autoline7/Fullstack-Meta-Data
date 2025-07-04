import { useState } from "react"
import { Card, CardContent, CardFooter } from "@/components/ui/card"
import { Label } from "@/components/ui/label"
import { Input } from "@/components/ui/input"
import { Button } from "@/components/ui/button"
import { File, X } from "lucide-react"
import { uploader } from "@/lib/utils"

export function DataTableUploadFile() {
  const [showForm, setShowForm] = useState(false);
  const [files, setFiles] = useState<File[]>([]);
  const [isUploading, setIsUploading] = useState(false);

  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const selectedFiles = Array.from(e.target.files || []);
    
    // Filter only JSON files
    const jsonFiles = selectedFiles.filter(file => 
      file.type === 'application/json' || file.name.endsWith('.json')
    );
    
    if (jsonFiles.length !== selectedFiles.length) {
      alert('Only JSON files are allowed');
    }
    
    setFiles(prev => [...prev, ...jsonFiles]);
  }

  const removeFile = (index: number) => {
    setFiles(prev => prev.filter((_, i) => i !== index));
  }

  const handleUpload = async () => {
    if (files.length === 0) return;

    setIsUploading(true);
    
    try {
      const formData = new FormData();
      
      // Append all files to FormData
      files.forEach((file, index) => {
        formData.append(`files`, file);
      });

      console.log(await uploader("api/instagram/upload", formData));

    alert(`${files.length} file(s) uploaded successfully`);
    setFiles([]);
    setShowForm(false);
  } catch (error: any) {
    alert(`Upload failed: ${error?.response?.data || error.message}`);
  } finally {
    setIsUploading(false);
  }
  }

  const handleDragOver = (e: React.DragEvent) => {
    e.preventDefault();
  }

  const handleDrop = (e: React.DragEvent) => {
    e.preventDefault();
    const droppedFiles = Array.from(e.dataTransfer.files);
    
    // Filter only JSON files
    const jsonFiles = droppedFiles.filter(file => 
      file.type === 'application/json' || file.name.endsWith('.json')
    );
    
    if (jsonFiles.length !== droppedFiles.length) {
      alert('Only JSON files are allowed');
    }
    
    setFiles(prev => [...prev, ...jsonFiles]);
  }

  return (
    <>
      {/* Add Task Button */}
      <Button size="sm" className="ml-3 hidden h-8 lg:flex" onClick={() => setShowForm(true)}>
        + Add Task
      </Button>

      {/* Backdrop + Centered Card */}
      {showForm && (
        <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/50">
          <div className="relative w-[500px] max-h-[80vh] overflow-auto">
            <Card className="shadow-xl">
              <CardContent className="p-6 space-y-4">
                {/* Drag and Drop Area */}
                <div 
                  className="border-2 border-dashed border-gray-200 rounded-lg flex flex-col gap-1 p-6 items-center hover:border-gray-300 transition-colors cursor-pointer"
                  onDragOver={handleDragOver}
                  onDrop={handleDrop}
                  onClick={() => document.getElementById('file-upload')?.click()}
                >
                  <File className="w-12 h-12 text-gray-400" />
                  <span className="text-sm font-medium text-gray-500">
                    Drag and drop JSON files or click to browse
                  </span>
                  <span className="text-xs text-gray-500">
                    Multiple JSON files supported
                  </span>
                </div>

                <input
                  id="file-upload"
                  type="file"
                  accept=".json,application/json"
                  multiple
                  className="hidden"
                  onChange={handleFileChange}
                />
                
                {/* File Upload Input */}
                <div className="space-y-2 text-sm">
                  <Label htmlFor="file-input" className="text-sm font-medium">
                    Or select files manually
                  </Label>
                  <Input 
                    id="file-input" 
                    type="file" 
                    accept=".json,application/json" 
                    multiple
                    className="cursor-pointer"
                    onChange={handleFileChange}
                  />
                </div>

                {/* Selected Files List */}
                {files.length > 0 && (
                  <div className="space-y-2">
                    <Label className="text-sm font-medium">
                      Selected Files ({files.length})
                    </Label>
                    <div className="max-h-40 overflow-y-auto space-y-2">
                      {files.map((file, index) => (
                        <div key={`${file.name}-${index}`} className="flex items-center justify-between p-2 bg-gray-50 rounded-md">
                          <div className="flex items-center space-x-2">
                            <File className="w-4 h-4 text-gray-500" />
                            <span className="text-sm text-gray-700 truncate">{file.name}</span>
                            <span className="text-xs text-gray-500">
                              ({(file.size / 1024).toFixed(1)} KB)
                            </span>
                          </div>
                          <Button
                            variant="ghost"
                            size="sm"
                            onClick={() => removeFile(index)}
                            className="h-6 w-6 p-0 hover:bg-red-100"
                          >
                            <X className="w-3 h-3" />
                          </Button>
                        </div>
                      ))}
                    </div>
                  </div>
                )}
              </CardContent>
              <CardFooter className="flex justify-between">
                <Button
                  variant="ghost"
                  onClick={() => {
                    setShowForm(false);
                    setFiles([]);
                  }}
                  disabled={isUploading}
                >
                  Cancel
                </Button>
                <Button 
                  onClick={handleUpload} 
                  disabled={files.length === 0 || isUploading} 
                  size="lg"
                >
                  {isUploading ? 'Uploading...' : `Upload ${files.length} file(s)`}
                </Button>
              </CardFooter>
            </Card>
          </div>
        </div>
      )}
    </>
  )
}